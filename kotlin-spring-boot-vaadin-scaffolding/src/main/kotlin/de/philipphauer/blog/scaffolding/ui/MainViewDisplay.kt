package de.philipphauer.blog.scaffolding.ui

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewDisplay
import com.vaadin.spring.annotation.SpringViewDisplay
import com.vaadin.ui.Component
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme


@SpringViewDisplay
class MainViewDisplay : Panel(), ViewDisplay {
    init {
        styleName = ValoTheme.PANEL_BORDERLESS
    }

    override fun showView(view: View) {
        content = view as Component
    }
}