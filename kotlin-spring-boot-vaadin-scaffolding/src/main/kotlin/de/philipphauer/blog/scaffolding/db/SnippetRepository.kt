package de.philipphauer.blog.scaffolding.db

import org.springframework.data.mongodb.repository.MongoRepository

interface SnippetRepository : MongoRepository<SnippetEntity, String>

