package de.philipphauer.blog.scaffolding.ui

import com.vaadin.server.FontAwesome
import de.philipphauer.blog.scaffolding.db.SnippetState

fun SnippetState.toIcon() = when (this){
    SnippetState.EXECUTION_SUCCESS -> FontAwesome.THUMBS_O_UP
    SnippetState.EXECUTION_FAIL -> FontAwesome.THUMBS_O_DOWN
}

fun SnippetState.toLabel() = when (this){
    SnippetState.EXECUTION_SUCCESS -> "Successfully executed"
    SnippetState.EXECUTION_FAIL -> "Execution failed"
}