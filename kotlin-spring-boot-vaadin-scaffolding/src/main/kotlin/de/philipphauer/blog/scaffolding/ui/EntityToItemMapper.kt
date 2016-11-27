package de.philipphauer.blog.scaffolding.ui

import de.philipphauer.blog.scaffolding.db.SnippetEntity

fun mapToItems(entities: List<SnippetEntity>) = entities.map(::mapToItem)

fun mapToItem(entity: SnippetEntity) = SnippetOverviewItem(
        code = entity.code,
        date = entity.date,
        author = "${entity.author.firstName} ${entity.author.lastName}",
        state = entity.state
)
