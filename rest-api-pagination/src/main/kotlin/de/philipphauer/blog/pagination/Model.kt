package de.philipphauer.blog.pagination

/** a token points to the last element of the current page. "last" usually means "highest timestamp". **/
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
        /** use this with >= in the WHERE clause (the equals is important!) */
        val timestamp: Long,
        val limit: Int
)

data class Page(
        val entities: List<Pageable>,
        val currentToken: ContinuationToken?
)

interface Pageable {
    fun getIdentifier(): String
    fun getKey(): Long
}