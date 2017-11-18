package de.philipphauer.blog.pagination

data class Page(
        val results: List<Any>,
        /** contains continuation token */
        val nextPage: String
)

data class ContinuationToken(
        val timestamp: Long,
        val offset: Int,
        val checksum: Long
) {
    override fun toString() = "${timestamp}_${offset}_${checksum}"
}

interface Pageable {
    fun getIdentifier(): String
    fun getKey(): Long
}