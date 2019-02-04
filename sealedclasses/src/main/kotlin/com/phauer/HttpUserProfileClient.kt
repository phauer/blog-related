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

    fun downloadImage1(avatarUrl: String): ByteArray {
        return ByteArray(0)
    }

    fun downloadImage2(avatarUrl: String): ImageDownloadResult {
        return ImageDownloadResult.Success(ByteArray(0))
    }
}

class UserProfileClientException(message: String, cause: Exception? = null) : RuntimeException(message, cause)
class ImageDownloadException(message: String, cause: Exception? = null) : RuntimeException(message, cause)
class SuspiciousException(message: String, cause: Exception? = null) : RuntimeException(message, cause)

sealed class UserProfileResult {
    data class Success(val userProfile: UserProfileDTO) : UserProfileResult()
    data class Error(val message: String, val cause: Exception? = null) : UserProfileResult()
}

sealed class ImageDownloadResult {
    data class Success(val image: ByteArray) : ImageDownloadResult()
    data class Error(val message: String, val cause: Exception? = null) : ImageDownloadResult()
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

fun aMoreComplicatedExample() {
    val client = HttpUserProfileClient(restTemplate())
    val userId = "1"

    try {
        val profile = client.requestUserProfile1(userId)
        try {
            val image = client.downloadImage1(profile.avatarUrl)
            processImage(image)
        } catch (ex: ImageDownloadException) {
            queueForRetry(userId, ex.message)
        }
    } catch (ex: UserProfileClientException) {
        showMessageToUser(userId, ex.message)
    } catch (ex: SuspiciousException) {
        // which method throws this exception?
        // requestUserProfile1()? downloadImage1()? processImage()? queueForRetry()?
    }
    // have we forgot to catch an exception? Who knows.


    // vs

    when (val profileResult = client.requestUserProfile2(userId)) {
        is UserProfileResult.Success -> {
            when (val imageResult = client.downloadImage2(profileResult.userProfile.avatarUrl)) {
                is ImageDownloadResult.Success -> processImage(imageResult.image)
                is ImageDownloadResult.Error -> queueForRetry(userId, imageResult.message)
            }
        }
        is UserProfileResult.Error -> showMessageToUser(userId, profileResult.message)
    }


}

fun showMessageToUser(userId: String, message: String?) {


}

fun queueForRetry(userId: String, message: String?) {

}

fun processImage(image: ByteArray) {

}


fun queueDesignForRetry(designId: String, errorMessage: String) {
    println("queueJobForRetry $designId")
    println(errorMessage)
}

fun processUserProfile(value: UserProfileDTO) {
    println("processUserProfile $value")
}

