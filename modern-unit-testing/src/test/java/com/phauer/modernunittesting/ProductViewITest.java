package com.phauer.modernunittesting;

import com.github.mvysny.kaributesting.v10.GridKt;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.Instant;

import static com.github.mvysny.kaributesting.v10.LocatorJ._click;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductViewITest {

    private JdbcTemplate template;
    private ProductView view;

    @BeforeAll
    public void beforeAll() {
        MockVaadin.setup();
        PostgreSQLContainer db = new PostgreSQLContainer("postgres:11.2-alpine");
        db.start();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(db.getJdbcUrl())
                .build();
        template = new JdbcTemplate(dataSource);
    }

    @BeforeEach
    public void beforeEach() {
        ProductDAO dao = new ProductDAO(template);
        view = new ProductView(dao);
    }

    @Test
    public void databaseDataIsCorrectlyReturned() {
        insertIntoDatabase(
                createProductEntity(1, "Smartphone", 10, 5, Instant.ofEpochSecond(1)),
                createProductEntity(2, "Notebook", 12, 9, Instant.ofEpochSecond(2))
        );

        Button button = _get(view, Button.class, spec -> spec.withText("Load Products"));
        _click(button);

        Grid<ProductModel> grid = _get(view, Grid.class);
        assertThat(GridKt._size(grid)).isEqualTo(2);
        assertThat(GridKt._get(grid, 0)).isEqualTo(new ProductModel().setId("1").setName("Smartphone"));
    }

    private void insertIntoDatabase(Product smartphone, Product notebook) {

    }

    private Product createProductEntity(int i, String smartphone, int i1, int i2, Instant ofEpochSecond) {
        return new Product();
    }

}