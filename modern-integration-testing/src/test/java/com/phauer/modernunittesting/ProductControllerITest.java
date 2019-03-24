package com.phauer.modernunittesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
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
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerITest {

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
        template = new JdbcTemplate(dataSource);
        SchemaCreator.createSchema(template);
        ProductDAO dao = new ProductDAO(template);

        // TaxServiceClient
        taxService = new MockWebServer();
        taxService.start();
        TaxServiceClient client = new TaxServiceClient(taxService.url("").toString());

        // PriceCalculator
        PriceCalculator calculator = new PriceCalculator();

        // ProductController
        ProductController controller = new ProductController(dao, client, calculator);
        this.client = MockMvcBuilders
                .standaloneSetup(controller)
                .setViewResolvers(new InternalResourceViewResolver())
                .build();
    }

    @Test
    public void databaseDataIsCorrectlyReturned() throws Exception {
        insertIntoDatabase(
                new ProductEntity().setId("90").setName("Envelope"),
                new ProductEntity().setId("50").setName("Pen")
        );
        taxService.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(toJson(new TaxServiceResponseDTO(Locale.GERMANY, 0.19)))
        );

        String responseJson = client.perform(get("/products"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        assertThat(toDTOs(responseJson)).containsOnly(
                new ProductDTO().setId("90").setName("Envelope").setPrice(0.5),
                new ProductDTO().setId("50").setName("Pen").setPrice(0.5)
        );
        // or assert the data in the database if the request should change something.
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