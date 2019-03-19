package com.phauer.modernunittesting;

import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.vaadin.flow.component.button.Button;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Instant;

import static com.github.mvysny.kaributesting.v10.LocatorJ._click;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductViewITest {

    private ProductDAO dao;
    private ProductView view;

    @BeforeAll
    public void beforeAll() {
        MockVaadin.setup();
        PostgreSQLContainer db = new PostgreSQLContainer("postgres:11.2-alpine");
        db.start();
        dao = new ProductDAO(db.getJdbcUrl());
    }

    @BeforeEach
    public void beforeEach() {
        view = new ProductView(dao);
    }

    @Test
    public void databaseDataIsCorrectlyReturned() {
        insert(
                createProductEntity(1, "Smartphone", 10, 5, Instant.ofEpochSecond(1)),
                createProductEntity(2, "Notebook", 12, 9, Instant.ofEpochSecond(2))
        );

        Button button = _get(view, Button.class, spec -> spec.withText("Hello World"));
        _click(button);

        // TODO do something meaningful
//        view._get
        // TODO assert
//        final Grid<Product> grid = _get(Grid.class);
//        assertEquals(1, _size(grid));
    }

    private void insert(Product smartphone, Product notebook) {

    }

    private Product createProductEntity(int i, String smartphone, int i1, int i2, Instant ofEpochSecond) {
        return new Product();
    }

}