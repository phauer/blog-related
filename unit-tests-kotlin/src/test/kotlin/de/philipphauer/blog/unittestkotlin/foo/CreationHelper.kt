package de.philipphauer.blog.unittestkotlin.foo

import java.time.Instant
import java.util.Locale

data class Design(
    val id: Int,
    val userId: Int,
    val name: String,
    val fileName: String,
    val dateCreated: Instant,
    val dateModified: Instant,
    val tags: Map<Locale, List<Tag>>
)

data class Tag(
    val value: String
)

fun createDesign(
    id: Int = 1,
    name: String = "Cat",
    date: Instant = Instant.ofEpochSecond(1518278198),
    tags: Map<Locale, List<Tag>> = mapOf(
        Locale.US to listOf(Tag(value = "$name in English")),
        Locale.GERMANY to listOf(Tag(value = "$name in German"))
    )
) = Design(
    id = id,
    userId = 9,
    name = name,
    fileName = name,
    dateCreated = date,
    dateModified = date,
    tags = tags
)

//usage:
val testDesign = createDesign()
val testDesign2 = createDesign(id = 1, name = "Fox")
val testDesign3 = createDesign(id = 1, name = "Fox", tags = mapOf())