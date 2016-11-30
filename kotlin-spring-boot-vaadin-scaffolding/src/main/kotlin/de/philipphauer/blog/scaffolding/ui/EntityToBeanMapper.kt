package de.philipphauer.blog.scaffolding.ui

import de.philipphauer.blog.scaffolding.db.SnippetEntity

fun mapToBeans(entities: List<SnippetEntity>) = entities.map(::mapToBean)

fun mapToBean(entity: SnippetEntity) = SnippetOverviewBean(
        code = entity.code,
        date = entity.date,
        author = "${entity.author.firstName} ${entity.author.lastName}",
        state = entity.state
)
