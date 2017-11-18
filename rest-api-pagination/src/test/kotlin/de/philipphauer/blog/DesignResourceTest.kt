package de.philipphauer.blog

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.junit.jupiter.api.Test


internal class DesignResourceTest {

    @Test
    fun getDesigns() {
        val resource = DesignResource()

        val response = resource.getDesigns(Request(Method.GET, "/designs"))

        assertThat(response.toPageable().nextPage).contains("?continue=TODO")
    }
}

private val mapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
private fun Response.toPageable() = mapper.readValue(bodyString(), Pageable::class.java)

data class Pageable(
        val nextPage: String
)