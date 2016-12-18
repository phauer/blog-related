package de.philipphauer.blog.scaffolding.ui.views

import com.vaadin.data.util.BeanItem
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.data.util.converter.Converter
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Button
import com.vaadin.ui.Table
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import de.philipphauer.blog.scaffolding.MyAppProps
import de.philipphauer.blog.scaffolding.db.SnippetRepository
import de.philipphauer.blog.scaffolding.ui.DetailsWindow
import de.philipphauer.blog.scaffolding.ui.Labels
import de.philipphauer.blog.scaffolding.ui.PropertyIds
import de.philipphauer.blog.scaffolding.ui.SnippetOverviewBean
import de.philipphauer.blog.scaffolding.ui.mapToBeans
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.annotation.PostConstruct


@SpringView(name = OverviewView.VIEW_NAME)
class OverviewView(val repo: SnippetRepository, val props: MyAppProps) : VerticalLayout(), View {

    companion object {
        const val VIEW_NAME = ""
        const val LABEL = "Overview"
    }

    override fun enter(event: ViewChangeListener.ViewChangeEvent) {
    }

    @PostConstruct
    internal fun init() {
        val snippetEntities = repo.findAll()
        val snippetBeans = mapToBeans(snippetEntities)
        val container = BeanItemContainer(SnippetOverviewBean::class.java, snippetBeans)
        val table = Table(null, container).apply {
            setSizeFull()
            setColumnHeader(PropertyIds.CODE, Labels.CODE)
            setColumnHeader(PropertyIds.AUTHOR, Labels.AUTHOR)
            setColumnHeader(PropertyIds.DATE, Labels.DATE)
            setColumnHeader(PropertyIds.STATE, Labels.STATE)
            sort(arrayOf(PropertyIds.DATE), booleanArrayOf(false))
            addGeneratedColumn(PropertyIds.CODE, ShortenedValueColumnGenerator)
            addGeneratedColumn("Details", ::generateDetailsButton)
            setConverter(PropertyIds.DATE, StringToInstantConverter)
        }
        setSizeFull()
        addComponent(table)
    }
}

//a) ColumnGenerator as singleton object (you want to want to group multiple fields and methods OR when your have more than one method (e.g. Converter))
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

//b) ColumnGenerator as top-level function. Pass as method reference. very concise.
private fun generateDetailsButton(source: Table, itemId: Any, columnId: Any) = Button("Details").apply {
    addStyleName(ValoTheme.BUTTON_LINK)
    addClickListener {
        val item = source.getItem(itemId) as BeanItem<SnippetOverviewBean>
        val window = DetailsWindow(item.bean)
        UI.getCurrent().addWindow(window)
    }
}

//"object" singleton useful for interfaces with more than one method. only if stateless.
object StringToInstantConverter : Converter<String, Instant> {
    private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z")
            .withLocale(Locale.UK)
            .withZone(ZoneOffset.UTC)

    override fun getPresentationType() = String::class.java
    override fun getModelType() = Instant::class.java

    override fun convertToPresentation(value: Instant?, targetType: Class<out String>?, locale: Locale?)
            = DATE_FORMATTER.format(value)!!

    override fun convertToModel(value: String?, targetType: Class<out Instant>?, locale: Locale?): Instant {
        throw UnsupportedOperationException("Not yet implemented")
    }
}