package de.philipphauer.blog.misc.vaadin;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

public class ActionListenerLambdaExampleJava {

    private void bla(){
        Button b = new Button();
        b.addClickListener(this::greet);
        b.addClickListener(event -> greet(event));
        b.addClickListener(event -> {greet(event);});
    }

    private void greet(Button.ClickEvent event){
        Notification.show("Hello!");
    }

}
