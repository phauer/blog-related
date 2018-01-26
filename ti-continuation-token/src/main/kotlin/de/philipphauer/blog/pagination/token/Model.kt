package de.philipphauer.blog.pagination.token

interface Pageable {
    val id: String
    val timestamp: Long
}

data class ContinuationToken(
    val timestamp: Long,
    val id: String
) {
    override fun toString() = "${timestamp}_$id"
}

data class Page<out T : Pageable>(
    val entities: List<T>,
    val token: ContinuationToken?,
    val hasNext: Boolean
)