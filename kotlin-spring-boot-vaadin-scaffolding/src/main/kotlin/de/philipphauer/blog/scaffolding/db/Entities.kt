package de.philipphauer.blog.scaffolding.db

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection="snippets")
data class SnippetEntity @PersistenceConstructor constructor(
        @Id val id: ObjectId? = null, //default parameter value requires @PersistenceConstructor
        val code: String,
        val author: AuthorEntity,
        val date: Instant,
        val state: SnippetState
)

data class AuthorEntity(
        val firstName: String,
        val lastName: String
)

enum class SnippetState {ACTIVATED, DEACTIVATED}