package com.phauer

import org.slf4j.LoggerFactory


fun main(args: Array<String>) {
    println("Hello, World")
    val logger = LoggerFactory.getLogger("asdf")
    val client = DesignClient()
    //Usage
    when (val result = client.retrieveDesigns()) {
        is Outcome.Success -> processDesigns(result.value)
        is Outcome.Error -> logger.error(result.message, result.cause)

    }
}

fun processDesigns(value: Any) {


}

sealed class Outcome<out T: Any> {
    data class Success<out T : Any>(val value: T): Outcome<T>()
    data class Error(val message: String, val cause: Exception? = null): Outcome<Nothing>()
}


class DesignClient{
    fun retrieveDesigns(): Outcome<Design> {
        return Outcome.Success(value = Design("Dog"))
    }
}

data class Design(
    val name: String
)