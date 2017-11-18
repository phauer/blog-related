package de.philipphauer.blog

import de.philipphauer.blog.pagination.Pageable
import java.time.Instant

data class Design(
        val id: String,
        val title: String,
        val imageUrl: String,
        val dateModified: Instant
): Pageable {
    override fun getIdentifier() = id
    override fun getKey() = dateModified.epochSecond
}