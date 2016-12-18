package de.philipphauer.blog.misc

import java.time.Instant

data class BlogEntity (
        val author: AuthorEntity,
        val date: Instant,
        val content: String
)
data class AuthorEntity(val firstName: String, val lastName: String)
