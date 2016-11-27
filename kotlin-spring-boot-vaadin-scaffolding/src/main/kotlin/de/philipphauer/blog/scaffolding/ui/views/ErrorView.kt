package de.philipphauer.blog.scaffolding.ui.views

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.SpringView
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import javax.annotation.PostConstruct


@SpringComponent
@UIScope
@SpringView
class ErrorView : VerticalLayout(), View {

    override fun enter(event: ViewChangeEvent) {
    }

    @PostConstruct
    internal fun init() {
        addComponent(Label("Invalid View Fragment in URL!"))
    }
}