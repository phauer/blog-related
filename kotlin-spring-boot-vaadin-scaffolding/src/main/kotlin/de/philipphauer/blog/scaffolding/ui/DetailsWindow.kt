package de.philipphauer.blog.scaffolding.ui

import com.vaadin.server.FontAwesome
import com.vaadin.server.Sizeable
import com.vaadin.shared.ui.label.ContentMode
import com.vaadin.ui.Button
import com.vaadin.ui.FormLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Window

class DetailsWindow(snippet: SnippetOverviewBean) : Window(){
    init {
        caption = "Snippet Details"
        isModal = true
        val layout = FormLayout().apply {
            setMargin(true)
            isSpacing = true
            val codeLabel = Label().apply {
                contentMode = ContentMode.HTML
                caption = Labels.CODE
                value = snippet.code
            }
            val stateLabel = Label().apply {
                contentMode = ContentMode.HTML
                caption = Labels.STATE
                value = "${snippet.state.toIcon().html} ${snippet.state.toLabel()}"
            }
            val authorLabel = Label().apply {
                caption = Labels.AUTHOR
                value = snippet.author
            }
            val closeButton = Button("Close", FontAwesome.CLOSE).apply {
                addClickListener { close() }
            }
            addComponents(codeLabel, authorLabel, stateLabel, closeButton)
        }
        setWidth(50F, Sizeable.Unit.PERCENTAGE)
        setHeight(50F, Sizeable.Unit.PERCENTAGE)
        center()
        content = layout
    }
}
