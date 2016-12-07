package de.philipphauer.blog.scaffolding.ui

import com.vaadin.navigator.View
import com.vaadin.server.FontAwesome
import com.vaadin.server.Page
import com.vaadin.server.Resource
import com.vaadin.spring.annotation.SpringView
import com.vaadin.spring.navigator.SpringNavigator
import com.vaadin.ui.MenuBar
import com.vaadin.ui.themes.ValoTheme
import de.philipphauer.blog.scaffolding.ui.views.CreateSnippetView
import de.philipphauer.blog.scaffolding.ui.views.ErrorView
import de.philipphauer.blog.scaffolding.ui.views.OverviewView

open class NavigationPresenter(val navigator: SpringNavigator){

    val menu: MenuBar
    private val viewNameToMenuBar: Map<String, MenuBar.MenuItem>

    init {
        navigator.setErrorView(ErrorView::class.java)
        menu = MenuBar()
        menu.addStyleName(ValoTheme.MENUBAR_SMALL)
        menu.addStyleName(ValoTheme.MENUBAR_BORDERLESS)

        val overviewItem = createMenuItem(OverviewView.LABEL, OverviewView::class.java, FontAwesome.LIST)
        val createItem = createMenuItem(CreateSnippetView.LABEL, CreateSnippetView::class.java, FontAwesome.CODE)
        viewNameToMenuBar = mapOf(OverviewView.VIEW_NAME to overviewItem,
                CreateSnippetView.VIEW_NAME to createItem)
    }

    private fun createMenuItem(label: String, viewClass: Class<out View>, icon: Resource): MenuBar.MenuItem {
        val viewAnnotation = viewClass.getDeclaredAnnotation(SpringView::class.java)
        return menu.addItem(label, icon, MenuBar.Command { navigateTo(viewAnnotation.name )})
    }

    fun navigateTo(view: String) {
        navigator.navigateTo(view)
        disableMenuItem(view)
    }

    fun disableMenuItem(view: String) {
        for ((viewName, menuItem) in viewNameToMenuBar){
            menuItem.isEnabled = viewName != view
        }
    }

    fun disableCurrentMenuItem() {
        when (Page.getCurrent().uriFragment?.removePrefix("!")) {
            CreateSnippetView.VIEW_NAME -> disableMenuItem(CreateSnippetView.VIEW_NAME)
            null, OverviewView.VIEW_NAME -> disableMenuItem(OverviewView.VIEW_NAME)
        }
    }
}