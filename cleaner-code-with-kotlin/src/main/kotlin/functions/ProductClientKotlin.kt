package functions

import functions.ProductClient.Product
import functions.ProductClient.ProductClientException
import okhttp3.Response
import okhttp3.ResponseBody

class ProductClientKotlin {

    fun parseProductFromHttpBody(response: Response?) = when (response?.code()){
        null -> throw ProductClientException("Response is null!")
        200, 201 -> mapToDTO(response.body())
        in 400..499 -> throw ProductClientException("Send an invalid request.")
        in 500..599 -> throw ProductClientException("Server error.")
        else -> throw ProductClientException("Error. Code ${response.code()}")
    }

    private fun mapToDTO(body: ResponseBody?): Product {
        return Product()
    }

    fun parseProductFromHttpBody2(response: Response?): Product {
        val product = when (response?.code()) {
            null -> throw ProductClientException("Response is null!")
            200, 201 -> mapToDTO(response.body())
            in 400..499 -> throw ProductClientException("Send an invalid request to server.")
            in 500..599 -> throw ProductClientException("Server error.")
            else -> throw ProductClientException("Error. Code ${response.code()}")
        }
        return product
    }
}
