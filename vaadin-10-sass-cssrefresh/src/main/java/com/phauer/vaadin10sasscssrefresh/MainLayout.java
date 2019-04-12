package com.phauer.vaadin10sasscssrefresh;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;

@Push
@StyleSheet("frontend://styles/main.css")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends Div implements RouterLayout {
    public MainLayout() {
        setClassName("main-layout");
    }
}