package idiomaticKotlin

import java.time.Instant


data class SnippetDTO(
        val code: String,
        val author: String,
        val date: Instant
)
data class SnippetEntity(
        val code: String,
        val author: AuthorEntity,
        val date: Instant
)
data class AuthorEntity(
        val firstName: String,
        val lastName: String
)

// Don't
fun mapToDTO2(entity: SnippetEntity): SnippetDTO {
    val dto = SnippetDTO(
            code = entity.code,
            date = entity.date,
            author = "${entity.author.firstName} ${entity.author.lastName}"
    )
    return dto
}

// Do
// DONE easy, concise and readable mapping between value objects with single expression functions and named arguments
fun mapToDTO2(entity: SnippetEntity) = SnippetDTO(
        code = entity.code,
        date = entity.date,
        author = "${entity.author.firstName} ${entity.author.lastName}"
)

// even better: as an extension function
fun SnippetEntity.toDTO() = SnippetDTO(
        code = code,
        date = date,
        author = "${author.firstName} ${author.lastName}"
)

//val entity = SnippetEntity()
//val dto = entity.toDTO()