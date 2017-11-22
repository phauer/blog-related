package de.philipphauer.blog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.philipphauer.blog.pagination.ContinuationTokenParser
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

class DesignResource(val dao: DesignDAO) {

    //TODO no next page on last page
    //TODO checksum

    fun getDesigns(request: Request): Response {
        val token = request.query("continue")?.let {ContinuationTokenParser.parse(it)}
        val pageSize = request.query("pageSize")?.toInt() ?: 100
        val daoResult = dao.getDesigns(token, pageSize)
        val dto = PageDTO(
                results = daoResult.designs.map(::mapToDTO),
                nextPage = if (daoResult.token == null) null else "http://localhost:8000/designs?pageSize=$pageSize&continue=${daoResult.token}"
        )
        return Response(Status.OK)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(dto.toJson())
    }
}

private fun mapToDTO(entity: DesignEntity) = DesignDTO(
        id = entity.id,
        title = entity.title,
        imageUrl = entity.imageUrl,
        dateModified = entity.dateModified.epochSecond
)

data class DesignDTO(
        val id: String,
        val title: String,
        val imageUrl: String,
        val dateModified: Long
)
data class PageDTO(
        val results: List<DesignDTO>,
        val nextPage: String?
)

private val mapper = jacksonObjectMapper()
private fun Any.toJson() = mapper.writeValueAsString(this)

