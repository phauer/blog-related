package com.phauer

import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

/**
 * enum as a result can also be fine.
 */
class ImageAvailabilityClient(
    private val restTemplate: RestTemplate
) {
    fun checkAvailabilityState(imageUrl: String): ImageAvailabilityState = try {
        restTemplate.headForHeaders(imageUrl)
        ImageAvailabilityState.OK
    } catch (exception: HttpClientErrorException) {
        ImageAvailabilityState.UNAVAILABLE
    } catch (exception: ResourceAccessException) {
        ImageAvailabilityState.TEMPORARY_UNAVAILABLE
    } catch (exception: RestClientException) {
        ImageAvailabilityState.UNAVAILABLE
    }
}

enum class ImageAvailabilityState {
    OK,
    TEMPORARY_UNAVAILABLE,
    UNAVAILABLE
}

fun main() {
    val client = ImageAvailabilityClient(restTemplate())
    val imageUrl = "http://localhost/dog.jpg"
    when (client.checkAvailabilityState(imageUrl)) {
        ImageAvailabilityState.OK -> markImageAsAvailable(imageUrl)
        ImageAvailabilityState.UNAVAILABLE -> markImageAsUnavailable(imageUrl)
        ImageAvailabilityState.TEMPORARY_UNAVAILABLE -> {
            // do nothing right now and just wait for next execution cycle
        }
    }
}

fun markImageAsUnavailable(imageUrl: Any) {

}

fun markImageAsAvailable(imageUrl: Any) {

}
