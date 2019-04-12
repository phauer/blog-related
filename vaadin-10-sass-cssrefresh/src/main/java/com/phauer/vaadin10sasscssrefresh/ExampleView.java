package com.phauer.vaadin10sasscssrefresh;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class ExampleView extends VerticalLayout {

    public ExampleView(){
        addClassName("exampleView");
        add(
            new H1("Example View"),
            new Button("Do Something", this::addLabelToView)
        );
    }

    private void addLabelToView(ClickEvent<Button> event) {
        add(new Label("The current UI state (this label) is preserved on SASS/CSS changes. No browser refresh is required. No need to click through your application again and again."));
    }
}
