package de.philipphauer.blog.pagination

import de.philipphauer.blog.pagination.token.Pageable
import java.time.Instant

data class DesignEntity(
    override val id: String,
    val title: String,
    val imageUrl: String,
    private val dateModified: Instant
) : Pageable {
    override val timestamp = dateModified.epochSecond
}