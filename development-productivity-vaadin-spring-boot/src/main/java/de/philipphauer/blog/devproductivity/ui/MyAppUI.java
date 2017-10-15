package de.philipphauer.blog.devproductivity.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.philipphauer.blog.devproductivity.model.Role;
import de.philipphauer.blog.devproductivity.model.User;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringUI(path = "ui")
@Theme("mytheme")
public class MyAppUI extends UI {

    private Grid<User> table = new Grid<>(User.class);
    private Label heading = new Label("Development Productivity Demo");

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Development Productivity Demo");

        table.setSizeFull();
        table.setItems(generateDummyUsers());

        heading.addStyleName(ValoTheme.LABEL_H1);

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(heading);
        layout.addComponentsAndExpand(table);
        setContent(layout);
    }

    private List<User> generateDummyUsers() {
        Random random = new Random();
        return Stream.generate(() -> random.nextInt(9999))
                .limit(50)
                .map(uuid -> new User()
                        .setId(uuid)
                        .setFirstName("Paul")
                        .setLastName("Stark")
                        .setActive(true)
                        .setRole(Role.REGISTERED_USER)
                        .setActive(true))
                .collect(Collectors.toList());
    }
}
