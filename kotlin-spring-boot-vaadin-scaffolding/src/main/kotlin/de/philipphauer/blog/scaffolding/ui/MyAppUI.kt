package de.philipphauer.blog.scaffolding.ui

import com.vaadin.annotations.Theme
import com.vaadin.server.FontAwesome
import com.vaadin.server.Sizeable
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.ui.label.ContentMode
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.spring.navigator.SpringNavigator
import com.vaadin.ui.Label
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme


@SpringUI(path = "ui")
@Theme("mytheme")
class MyAppUI(val mainContent: MainViewDisplay, navigator: SpringNavigator) : UI() {

    val navigationPresenter = NavigationPresenter(navigator)

    override fun init(request: VaadinRequest) {
        page.setTitle("Kotlin, Spring Boot, Vaadin")
        content = VerticalLayout(createHeader(), navigationPresenter.menu, mainContent).apply{
            setExpandRatio(mainContent, 1f)
            setHeight(100f, Sizeable.Unit.PERCENTAGE)
            setMargin(true)
        }
        navigationPresenter.disableCurrentMenuItem()
    }

    private fun  createHeader(): Label {
        val heading = Label("${FontAwesome.CODE.html} My Application with Kotlin, Spring Boot and Vaadin").apply {
            styleName = ValoTheme.LABEL_HUGE
            contentMode = ContentMode.HTML
        }
        return heading
    }
}