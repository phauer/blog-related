package com.phauer.modernunittesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HelperFunctions {

    private MockWebServer taxService;
    private JdbcTemplate template;
    private MockMvc client;

    @BeforeAll
    public void setup() throws IOException {
//        DataSource dataSource = createDataSourceAndStartDatabaseIfNecessary();

        // ProductDAO
        PostgreSQLContainer db = new PostgreSQLContainer("postgres:11.2-alpine");
        db.start();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .username(db.getUsername())
                .password(db.getPassword())
                .url(db.getJdbcUrl())
                .build();
        this.template = new JdbcTemplate(dataSource);
        SchemaCreator.createSchema(template);
        ProductDAO dao = new ProductDAO(template);

        // TaxServiceClient
        this.taxService = new MockWebServer();
        taxService.start();
        TaxServiceClient client = new TaxServiceClient(taxService.url("").toString());

        // PriceCalculator
        PriceCalculator calculator = new PriceCalculator();

        // ProductController
        ProductController controller = new ProductController(dao, client, calculator);
        this.client = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(new InternalResourceViewResolver()).build();
    }

    // Don't
    @Test
    public void categoryQueryParameter() throws Exception {
        List<ProductEntity> products = List.of(
                new ProductEntity().setId("11").setName("Envelope").setCategory("Office").setDescription("An Envelope").setStockAmount(1),
                new ProductEntity().setId("22").setName("Pen").setCategory("Office").setDescription("A Pen").setStockAmount(1),
                new ProductEntity().setId("33").setName("Notebook").setCategory("Hardware").setDescription("A Notebook").setStockAmount(2)
        );
        for (ProductEntity product : products) {
            template.execute(createSqlInsertStatement(product));
        }

        String responseJson = client.perform(get("/products?category=Office"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        assertThat(toDTOs(responseJson))
                .extracting(ProductDTO::getId)
                .containsOnly("11", "22");
    }


    // Do
    @Test
    public void categoryQueryParameter2() throws Exception {
        insertIntoDatabase(
                createProductWithCategory("11", "Office"),
                createProductWithCategory("22", "Office"),
                createProductWithCategory("33", "Hardware")
        );

        String responseJson = requestProductsByCategory("Office");

        assertThat(toDTOs(responseJson))
                .extracting(ProductDTO::getId)
                .containsOnly("11", "22");
    }

    private String createSqlInsertStatement(ProductEntity product) {
        return "insert into products(id, name) values ('" + product.getId() + "', '" + product.getName() + "');";
    }

    private ProductEntity createProductWithCategory(String id, String category) {
        return new ProductEntity().setId(id).setName("Envelope").setCategory(category).setDescription("An Envelope").setStockAmount(1);
    }

    private String requestProductsByCategory(String category) throws Exception {
        return client.perform(get("/products?category=" + category))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
    }

    private List<ProductDTO> toDTOs(String string) throws IOException {
        TypeReference dtoType = new TypeReference<List<ProductDTO>>() {
        };
        return new ObjectMapper().readValue(string, dtoType);
    }

    private void insertIntoDatabase(ProductEntity... products) {
        for (ProductEntity product : products) {
            template.execute("insert into products(id, name) values ('" + product.getId() + "', '" + product.getName() + "');");
        }
    }

    private String toJson(TaxServiceResponseDTO taxServiceResponseDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(taxServiceResponseDTO);
    }

    private DataSource createDataSourceAndStartDatabaseIfNecessary() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create().driverClassName("org.postgresql.Driver");
        try {
            // e.g. if started once via `docker-compose up`. see docker-compose.yml.
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 5432), 100);
            socket.close();
            return builder.username("postgres").password("password")
                    .url("jdbc:postgresql://localhost:5432/")
                    .build();
        } catch (Exception ex) {
            PostgreSQLContainer db = new PostgreSQLContainer("postgres:11.2-alpine");
            db.start();
            return builder.username(db.getUsername()).password(db.getPassword())
                    .url(db.getJdbcUrl())
                    .build();
        }
    }

    @BeforeEach
    public void beforeEach() {
        template.execute("truncate table products");
    }
}