package com.phauer.modernunittesting;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Hello Vaadin")
public class ProductView extends VerticalLayout {

    private ProductDAO dao;

    public ProductView(ProductDAO dao) {
        this.dao = dao;
        Button button = new Button("Hello World");
        button.addClickListener(e -> System.out.println("Hello World"));
        add(button);
    }
}
