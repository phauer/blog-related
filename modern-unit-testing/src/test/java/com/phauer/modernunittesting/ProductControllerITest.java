package com.phauer.modernunittesting;

import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

// Pseudo-Code ahead!
// Let's ignore the framework-specific wiring of the HTTP stack for now.
public class ProductControllerITest {

    private MockWebServer taxService;
    private ProductDAO dao;
    private ProductController controller;

    @BeforeAll
    public void setup() throws IOException {
        PostgreSQLContainer db = new PostgreSQLContainer();
        db.start();
        taxService = new MockWebServer();
        taxService.start();

        dao = new ProductDAO(db.getJdbcUrl());
        TaxServiceClient client = new TaxServiceClient(taxService.url("").toString());
        PriceCalculator calculator = new PriceCalculator();

        controller = new ProductController(dao, client, calculator);
    }

    @Test
    public void databaseDataIsCorrectlyReturned() throws IOException {
        insertIntoDatabase(
                createProductEntity(1, "Smartphone", 10, 5, Instant.ofEpochSecond(1)),
                createProductEntity(2, "Notebook", 12, 9, Instant.ofEpochSecond(2))
        );
        setUpTaxServiceMockResponse(new MockResponse()
                .setResponseCode(200)
                .setBody(toJson(new TaxServiceResponseDTO(Locale.GERMANY, 0.19)))
        );

        Response response = requestResource("/products");

        assertThat(response.code()).isEqualTo(200);
        assertThat(toDTOs(response.body().string())).containsOnly(
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

    private List<ProductDTO> toDTOs(String string) {
        return null;
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

    private String toJson(TaxServiceResponseDTO taxServiceResponseDTO) {
        return null;
    }

    // implementation fo the helper methods insertIntoDatabase(),
    // createProductEntity(), createProductDTO(), requestAndGetProducts()
}