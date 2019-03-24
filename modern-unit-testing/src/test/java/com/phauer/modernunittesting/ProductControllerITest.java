package com.phauer.modernunittesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.Instant;
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
        // ProductDAO
        PostgreSQLContainer db = new PostgreSQLContainer("postgres:11.2-alpine");
        db.start();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(db.getJdbcUrl())
                .build();
        template = new JdbcTemplate(dataSource);
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
                createProductEntity(1, "Smartphone", 10, 5, Instant.ofEpochSecond(1)),
                createProductEntity(2, "Notebook", 12, 9, Instant.ofEpochSecond(2))
        );
        taxService.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(toJson(new TaxServiceResponseDTO(Locale.GERMANY, 0.19)))
        );

        String responseJson = client.perform(get("/products"))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        assertThat(toDTOs(responseJson)).containsOnly(
                createProductDTO("1", "Smartphone", 250.00),
                createProductDTO("2", "Notebook", 1000.00)
        );
        // or assert the data in the database if the request should change something.
    }

    private Response requestResource(String s) {
        return null;
    }

    private ProductDTO createProductDTO(String s, String smartphone, double v) {
        return null;
    }

    private List<ProductDTO> toDTOs(String string) throws IOException {
        TypeReference dtoType = new TypeReference<List<ProductDTO>>() {
        };
        return new ObjectMapper().readValue(string, dtoType);
    }

    private Response requestAndGetProducts(String s) {
        return null;
    }

    private void insertIntoDatabase(Object smartphone, Object notebook) {
    }

    private Object createProductEntity(int i, String smartphone, int i1, int i2, Instant ofEpochSecond) {
        return null;
    }

    private void setUpTaxServiceMockResponse(MockResponse setBody) {
    }

    private String toJson(TaxServiceResponseDTO taxServiceResponseDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(taxServiceResponseDTO);
    }

}