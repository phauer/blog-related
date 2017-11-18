package de.philipphauer.blog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

class DesignResource(val dao: DesignDAO) {

    fun getDesigns(request: Request): Response {
        val designs = dao.getDesigns()
        val dto = DesignsPage(
                designs = designs,
                nextPage = "https://www.domain.de/designs?continue=TODO"
        )
        return Response(Status.OK)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(dto.toJson())
    }
}

private val mapper = jacksonObjectMapper()
private fun DesignsPage.toJson() = mapper.writeValueAsString(this)

