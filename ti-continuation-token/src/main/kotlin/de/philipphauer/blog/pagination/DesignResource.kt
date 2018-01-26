package de.philipphauer.blog.pagination

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.philipphauer.blog.pagination.token.toContinuationToken
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import java.time.Instant

class DesignResource(private val dao: DesignDAO) {

    fun getDesigns(request: Request): Response {
        val modifiedSince = request.query("modifiedSince").toInstantOrNull()
        val token = request.query("continuationToken")?.toContinuationToken()
        val pageSize = request.query("pageSize")?.toInt() ?: 3
        val page = when {
            modifiedSince == null && token == null -> dao.getDesignsSince(Instant.ofEpochSecond(0), pageSize)
            modifiedSince != null && token == null -> dao.getDesignsSince(modifiedSince, pageSize)
            modifiedSince == null && token != null -> dao.getNextDesignPage(token, pageSize)
            else -> return Response(Status.BAD_REQUEST)
        }
        val dto = PageDTO(
            designs = page.entities.map(::mapToDTO),
            continuationToken = page.token?.toString(),
            hasNext = page.hasNext,
            nextPage = page.token?.let { "http://localhost:8000/designs?pageSize=$pageSize&continuationToken=${page.token}" }
        )
        return Response(Status.OK)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(dto.toJson())
    }
}

private fun String?.toInstantOrNull() = when (this) {
    null -> null
    else -> Instant.ofEpochSecond(this.toLong())
}

private fun mapToDTO(entity: DesignEntity) = DesignDTO(
    id = entity.id,
    title = entity.title,
    imageUrl = entity.imageUrl,
    dateModified = entity.timestamp
)

data class DesignDTO(
    val id: String,
    val title: String,
    val imageUrl: String,
    val dateModified: Long
)

data class PageDTO(
    val designs: List<DesignDTO>,
    val continuationToken: String?,
    val nextPage: String?,
    val hasNext: Boolean
)

private val mapper = jacksonObjectMapper()
private fun PageDTO.toJson() = mapper.writeValueAsString(this)

