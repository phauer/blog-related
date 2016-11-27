package de.philipphauer.blog.scaffolding.ui

import com.vaadin.server.FontAwesome
import de.philipphauer.blog.scaffolding.db.SnippetState

fun SnippetState.toIcon() = when (this){
    SnippetState.ACTIVATED -> FontAwesome.THUMBS_O_UP
    SnippetState.DEACTIVATED -> FontAwesome.THUMBS_O_DOWN
}

fun SnippetState.toLabel() = when (this){
    SnippetState.ACTIVATED -> "Activated"
    SnippetState.DEACTIVATED -> "Deactivated"
}