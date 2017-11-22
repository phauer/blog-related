package de.philipphauer.blog.pagination

import de.philipphauer.blog.pagination.ContinuationTokenParser.DELIMITER

/** a token points to the last element of the current page. "last" usually means "highest timestamp". **/
data class ContinuationToken(
        /** timestamp of the highest entity in the last page. */
        val timestamp: Long,
        /** offset = amount of entities with the highest timestamp in the last page, that have the same timestamp */
        val offset: Int,
        /** used to detect modifications during pagination */
        val checksum: Long
) {
    override fun toString() = "$timestamp$DELIMITER$offset$DELIMITER$checksum"
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
    fun getID(): String
    fun getTimestamp(): Long
}