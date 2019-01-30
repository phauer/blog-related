package com.phauer

import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.io.IOException

/**
 * Using a generic Outcome sealed class.
 */
class HttpUserProfileClient(
    private val restTemplate: RestTemplate
) {
    /**
     * Version 1: With Exceptions
     */
    @Throws(HttpUserProfileClientException::class)
    fun requestUserProfile1(userId: String): UserProfileDTO = try {
        val design =
            restTemplate.getForObject("http://localhost:5000/userProfiles/$userId", UserProfileDTO::class.java)!!
        design
    } catch (ex: IOException) {
        throw HttpUserProfileClientException(
            message = "Server request failed due to an IO exception. Id: $userId, Message: ${ex.message}",
            cause = ex
        )
    } catch (ex: RestClientException) {
        throw HttpUserProfileClientException(
            message = "Server request failed. Id: $userId. status code: ${(ex as? RestClientResponseException)?.rawStatusCode}. body: ${(ex as? RestClientResponseException)?.responseBodyAsString}",
            cause = ex
        )
    }

    /**
     * Version 2: With Sealed Classes
     */
    fun requestUserProfile2(userId: String): Outcome<UserProfileDTO> = try {
        val design = restTemplate.getForObject("http://localhost:5000/userProfiles/$userId", UserProfileDTO::class.java)
        Outcome.Success(value = design)
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

class HttpUserProfileClientException(message: String, cause: Exception? = null) : RuntimeException(message, cause)

data class UserProfileDTO(
    val id: String,
    val name: String,
    val avatarUrl: String
)

fun main() {
    val client = HttpUserProfileClient(restTemplate())
    val designId = "1"

    /*
     * Version 1
     */
    val avatarUrl = try {
        client.requestUserProfile1(designId)
    } catch (ex: HttpUserProfileClientException) {
        "http://localhost/defaultAvatar.png"
    }

    try {
        val result = client.requestUserProfile1(designId)
        processDesigns(result)
    } catch (ex: HttpUserProfileClientException) {
        queueDesignForRetry(designId, ex.message!!)
    }

    /*
     * Version 2
     */
    val avatarUrl2 = when (val result = client.requestUserProfile2(designId)) {
        is Outcome.Success -> result.value.avatarUrl
        is Outcome.Error -> "http://localhost/defaultAvatar.png"
    }

    when (val result = client.requestUserProfile2(designId)) {
        is Outcome.Success -> processDesigns(result.value)
        is Outcome.Error -> queueDesignForRetry(designId, result.message)
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

fun processDesigns(value: UserProfileDTO) {
    println("processDesigns $value")
}

