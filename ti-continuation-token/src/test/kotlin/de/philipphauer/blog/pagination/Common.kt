package de.philipphauer.blog.pagination

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.Response

val mapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

fun Response.toPageDTO(): PageDTO = mapper.readValue(bodyString(), PageDTO::class.java)