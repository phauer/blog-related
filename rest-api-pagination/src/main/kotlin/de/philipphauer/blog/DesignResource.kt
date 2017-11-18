package de.philipphauer.blog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.philipphauer.blog.pagination.ContinuationToken
import de.philipphauer.blog.pagination.Page
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

class DesignResource(val dao: DesignDAO) {

    fun getDesigns(request: Request): Response {
        val token = request.query("continue")?.toContinuationToken()
        val pageSize = request.query("pageSize")?.toInt() ?: 100
        val daoResult = dao.getDesigns(token, pageSize)
        val dto = Page(
                results = daoResult.designs,
                nextPage = "https://www.domain.de/daoResult?continue=${daoResult.token}"
        )
        return Response(Status.OK)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(dto.toJson())
    }
}

private fun String.toContinuationToken(): ContinuationToken{
    val parts = this.split("_")
    return ContinuationToken(
            timestamp = parts[0].toLong(),
            offset = parts[1].toInt(),
            checksum = parts[2].toLong()
    )
}

private val mapper = jacksonObjectMapper()
private fun Page.toJson() = mapper.writeValueAsString(this)

