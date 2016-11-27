package de.philipphauer.blog.scaffolding.ui.views

import com.vaadin.data.util.BeanItem
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Button
import com.vaadin.ui.Table
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import de.philipphauer.blog.scaffolding.MyAppConfig
import de.philipphauer.blog.scaffolding.db.SnippetRepository
import de.philipphauer.blog.scaffolding.ui.DetailsWindow
import de.philipphauer.blog.scaffolding.ui.Labels
import de.philipphauer.blog.scaffolding.ui.PropertyIds
import de.philipphauer.blog.scaffolding.ui.SnippetOverviewItem
import de.philipphauer.blog.scaffolding.ui.mapToItems
import javax.annotation.PostConstruct


@SpringView(name = OverviewView.VIEW_NAME)
class OverviewView(val repo: SnippetRepository, val config: MyAppConfig) : VerticalLayout(), View {

    companion object {
        const val VIEW_NAME = ""
        const val LABEL = "Overview"
    }

    override fun enter(event: ViewChangeListener.ViewChangeEvent) {
    }

    @PostConstruct
    internal fun init() {
        val snippetEntities = repo.findAll()
        val snippetItems = mapToItems(snippetEntities)
        val container = BeanItemContainer(SnippetOverviewItem::class.java, snippetItems)
        val table = Table(null, container).apply {
            setSizeFull()
            setColumnHeader(PropertyIds.CODE, Labels.CODE)
            setColumnHeader(PropertyIds.AUTHOR, Labels.AUTHOR)
            setColumnHeader(PropertyIds.DATE, Labels.DATE)
            setColumnHeader(PropertyIds.STATE, Labels.STATE)
            sort(arrayOf(PropertyIds.DATE), booleanArrayOf(false))
            addGeneratedColumn(PropertyIds.CODE, ShortenedValueColumnGenerator)
            addGeneratedColumn("Details", DetailsLinkColumnGenerator)
        }
        setSizeFull()
        addComponent(table)
    }
}

private object ShortenedValueColumnGenerator : Table.ColumnGenerator {
    private val MAX_LENGTH = 20

    override fun generateCell(source: Table, itemId: Any, columnId: Any): Any?{
        val log = source.getItem(itemId).getItemProperty(columnId).value as? String
        return log?.shortenWithEllipsis()
    }

    fun String.shortenWithEllipsis(): String{
        if (this.length > MAX_LENGTH){
            return "${this.substring(0, MAX_LENGTH)}..."
        }
        return this
    }
}

private object DetailsLinkColumnGenerator : Table.ColumnGenerator {
    override fun generateCell(source: Table, itemId: Any, columnId: Any): Any {
        return Button("Details").apply {
            addStyleName(ValoTheme.BUTTON_LINK)
            addClickListener {
                val item = source.getItem(itemId) as BeanItem<SnippetOverviewItem>
                val window = DetailsWindow(item.bean)
                UI.getCurrent().addWindow(window)
            }
        }
    }
}