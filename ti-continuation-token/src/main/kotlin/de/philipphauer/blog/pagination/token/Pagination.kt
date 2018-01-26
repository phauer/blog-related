package de.philipphauer.blog.pagination.token

@Throws(InvalidContinuationTokenException::class)
fun String?.toContinuationToken(): ContinuationToken? {
    this ?: return null
    val parts = this.split("_")
    if (parts.size != 2) {
        throw createException(this, null)
    }
    try {
        val timestamp = java.lang.Long.parseUnsignedLong(parts[0])
        val id = parts[1]
        return ContinuationToken(timestamp, id)
    } catch (ex: Exception) {
        throw createException(this, ex)
    }
}

fun <T : Pageable> createPage(
    entities: List<T>,
    previousToken: ContinuationToken?,
    pageSize: Int
): Page<T> = Page(
    entities = entities,
    token = if (entities.isEmpty()) previousToken else createToken(entities),
    hasNext = entities.size >= pageSize
)

private fun <T : Pageable> createToken(entities: List<T>): ContinuationToken {
    val lastEntity = entities.last()
    return ContinuationToken(lastEntity.timestamp, lastEntity.id)
}

private fun createException(token: String, ex: Exception?): InvalidContinuationTokenException {
    return InvalidContinuationTokenException("Invalid token '$token'", ex)
}

class InvalidContinuationTokenException(msg: String, cause: Exception?) : RuntimeException(msg, cause)