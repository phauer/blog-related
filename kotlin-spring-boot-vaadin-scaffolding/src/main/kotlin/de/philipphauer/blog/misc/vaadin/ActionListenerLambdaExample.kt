package de.philipphauer.blog.misc.vaadin

import com.vaadin.ui.Button
import com.vaadin.ui.Notification


class ActionListenerLambdasExampleView {

    private fun init() {
        val button = Button("Click Me")
        button.addClickListener(::greet)

        button.addClickListener{event -> greet(event)}

        button.addClickListener{greet(it)}
    }
}
private fun greet(event: Button.ClickEvent){
    Notification.show("Hello!")
}

