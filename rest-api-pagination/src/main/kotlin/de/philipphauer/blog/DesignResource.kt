package de.philipphauer.blog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import java.net.URL

class DesignResource {

    fun getDesigns(request: Request): Response {
        val dto = DesignsPageDTO(
                designs = listOf(
                        DesignDTO("1", "Cat", URL("https://www.domain.de/cat.jpg"))
                ),
                nextPage = URL("https://www.domain.de/designs?continue=TODO")
        )
        return Response(Status.OK)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(dto.toJson())
    }
}

private val mapper = jacksonObjectMapper()
private fun DesignsPageDTO.toJson() = mapper.writeValueAsString(this)

