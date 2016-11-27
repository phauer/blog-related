package de.philipphauer.blog.scaffolding.ui

import de.philipphauer.blog.scaffolding.db.SnippetState
import java.time.Instant

data class SnippetOverviewItem(
        val code: String,
        val author: String,
        val date: Instant,
        val state: SnippetState
)
data class SnippetCreationItem(
        var code: String? = null,
        var author: String? = null
)

object PropertyIds{
    const val CODE = "code"
    const val AUTHOR = "author"
    const val DATE = "date"
    const val STATE = "state"
}
object Labels{
    const val CODE = "Code"
    const val AUTHOR = "Author"
    const val DATE = "Date"
    const val STATE = "State"
}
