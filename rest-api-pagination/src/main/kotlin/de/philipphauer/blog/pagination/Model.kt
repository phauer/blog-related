package de.philipphauer.blog.pagination

data class ContinuationToken(
        /** key/timestamp of the highest entity in the last page. */
        val timestamp: Long,
        /** offset = amount of entities with the highest key/timestamp in the last page, that have the same key/timestamp */
        val offset: Int,
        /** used to detect modifications during pagination */
        val checksum: Long
) {
    override fun toString() = "${timestamp}_${offset}_${checksum}"
}

data class QueryAdvice(
        val limit: Int,
        /** use this with >= in the WHERE clause (the equals is important!) */
        val timestamp: Long
)

data class Page(
        val entities: List<Pageable>,
        val currentToken: ContinuationToken?,
        val nextToken: ContinuationToken
)

interface Pageable {
    fun getIdentifier(): String
    fun getKey(): Long
}