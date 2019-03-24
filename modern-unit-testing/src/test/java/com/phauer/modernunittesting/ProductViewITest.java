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
                .username(db.getUsername())
                .password(db.getPassword())
                .url(db.getJdbcUrl())
                .build();
        template = new JdbcTemplate(dataSource);
        SchemaCreator.createSchema(template);
    }

    @BeforeEach
    public void beforeEach() {
        ProductDAO dao = new ProductDAO(template);
        view = new ProductView(dao);
    }

    @Test
    public void prodcutsAreCorrectlyDisplayedInTable() {
        insertIntoDatabase(
                new ProductEntity().setId("90").setName("Envelope"),
                new ProductEntity().setId("50").setName("Pen")
        );

        Button button = _get(view, Button.class, spec -> spec.withText("Load Products"));
        _click(button);

        Grid<ProductModel> grid = _get(view, Grid.class);
        assertThat(GridKt._size(grid)).isEqualTo(2);
        assertThat(GridKt._get(grid, 0))
                .isEqualTo(new ProductModel().setId("90").setName("Envelope"));
    }

    private void insertIntoDatabase(ProductEntity... products) {
        for (ProductEntity product : products) {
            template.execute("insert into products(id, name) values ('" + product.getId() + "', '" + product.getName() + "');");
        }
    }
}