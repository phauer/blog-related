package com.phauer

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.converter.BufferedImageHttpMessageConverter
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

/* The name "Result" conflicts with a class from the Kotlin std lib. */
sealed class Outcome<out T : Any> {
    data class Success<out T : Any>(val value: T) : Outcome<T>()
    data class Error(val message: String, val cause: Exception? = null) : Outcome<Nothing>()
}

val <T> T.exhaustive: T
    get() = this


fun restTemplate(): RestTemplate {
    val mapper = ObjectMapper().registerKotlinModule()
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

    return RestTemplate().apply {
        messageConverters = listOf(
            MappingJackson2HttpMessageConverter(mapper),
            StringHttpMessageConverter(StandardCharsets.UTF_8),
            BufferedImageHttpMessageConverter(),
            ByteArrayHttpMessageConverter()
        )
    }
}