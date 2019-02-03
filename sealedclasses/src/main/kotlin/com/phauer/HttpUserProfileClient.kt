package com.phauer

import com.phauer.common.Outcome
import com.phauer.common.exhaustive
import com.phauer.common.restTemplate
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.io.IOException

/**
 */
class HttpUserProfileClient(
    private val restTemplate: RestTemplate
) {
    /**
     * Version 1: With Exceptions
     */
    @Throws(UserProfileClientException::class) // this (or javadoc) may help to document the exception...
    fun requestUserProfile1(userId: String): UserProfileDTO = try {
        val userProfile =
            restTemplate.getForObject("http://localhost:5000/userProfiles/$userId", UserProfileDTO::class.java)!!
        userProfile
    } catch (ex: IOException) {
        throw UserProfileClientException(
            message = "Server request failed due to an IO exception. Id: $userId, Message: ${ex.message}",
            cause = ex
        )
    } catch (ex: RestClientException) {
        throw UserProfileClientException(
            message = "Server request failed. Id: $userId. status code: ${(ex as? RestClientResponseException)?.rawStatusCode}. body: ${(ex as? RestClientResponseException)?.responseBodyAsString}",
            cause = ex
        )
    }

    /**
     * Version 2: With Sealed Classes (domain-specific result class)
     */
    fun requestUserProfile2(userId: String): UserProfileResult = try {
        val userProfile =
            restTemplate.getForObject("http://localhost:5000/userProfiles/$userId", UserProfileDTO::class.java)
        UserProfileResult.Success(userProfile = userProfile)
    } catch (ex: IOException) {
        UserProfileResult.Error(
            message = "Server request failed due to an IO exception. Id: $userId, Message: ${ex.message}",
            cause = ex
        )
    } catch (ex: RestClientException) {
        UserProfileResult.Error(
            message = "Server request failed. Id: $userId. status code: ${(ex as? RestClientResponseException)?.rawStatusCode}. body: ${(ex as? RestClientResponseException)?.responseBodyAsString}",
            cause = ex
        )
    }


    /**
     * Version 3: With Sealed Classes (generic Result classes)
     */
    fun requestUserProfile3(userId: String): Outcome<UserProfileDTO> = try {
        val userProfile =
            restTemplate.getForObject("http://localhost:5000/userProfiles/$userId", UserProfileDTO::class.java)
        Outcome.Success(value = userProfile)
    } catch (ex: IOException) {
        Outcome.Error(
            message = "Server request failed due to an IO exception. Id: $userId, Message: ${ex.message}",
            cause = ex
        )
    } catch (ex: RestClientException) {
        Outcome.Error(
            message = "Server request failed. Id: $userId. status code: ${(ex as? RestClientResponseException)?.rawStatusCode}. body: ${(ex as? RestClientResponseException)?.responseBodyAsString}",
            cause = ex
        )
    }
}

class UserProfileClientException(message: String, cause: Exception? = null) : RuntimeException(message, cause)

sealed class UserProfileResult {
    data class Success(val userProfile: UserProfileDTO) : UserProfileResult()
    data class Error(val message: String, val cause: Exception? = null) : UserProfileResult()
}

data class UserProfileDTO(
    val id: String,
    val name: String,
    val avatarUrl: String
)

fun main() {
    val client = HttpUserProfileClient(restTemplate())
    val userId = "1"

    /*
     * Version 1
     */
    val avatarUrl = try {
        client.requestUserProfile1(userId).avatarUrl
    } catch (ex: UserProfileClientException) {
        "http://localhost/defaultAvatar.png"
    }

    try {
        val result = client.requestUserProfile1(userId)
        processUserProfile(result)
    } catch (ex: UserProfileClientException) {
        queueDesignForRetry(userId, ex.message!!)
    }

    /*
     * Version 2
     */
    val avatarUrl2 = when (val result = client.requestUserProfile2(userId)) {
        is UserProfileResult.Success -> result.userProfile.avatarUrl
        is UserProfileResult.Error -> "http://localhost/defaultAvatar.png"
    }

    when (val result = client.requestUserProfile2(userId)) {
        is UserProfileResult.Success -> processUserProfile(result.userProfile)
        is UserProfileResult.Error -> queueDesignForRetry(userId, result.message)
    }.exhaustive

    /*
     * Version 3
     */
    val avatarUrl3 = when (val result = client.requestUserProfile3(userId)) {
        is Outcome.Success -> result.value.avatarUrl
        is Outcome.Error -> "http://localhost/defaultAvatar.png"
    }

    when (val result = client.requestUserProfile3(userId)) {
        is Outcome.Success -> processUserProfile(result.value)
        is Outcome.Error -> queueDesignForRetry(userId, result.message)
    }.exhaustive


    /*
     Readability
     Safety (force to handle error case. adding new error type)
     Easy to Use (compiler guides us)
     less-error prone (no uncaught runtime exceptions, what about adding new exception types?)
     FP-compatibility (no side-effects)
     */
}


fun queueDesignForRetry(designId: String, errorMessage: String) {
    println("queueJobForRetry $designId")
    println(errorMessage)
}

fun processUserProfile(value: UserProfileDTO) {
    println("processUserProfile $value")
}

