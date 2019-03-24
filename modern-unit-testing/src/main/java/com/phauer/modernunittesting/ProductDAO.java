package com.phauer.modernunittesting;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDAO {

    private final JdbcTemplate template;

    public ProductDAO(JdbcTemplate template) {
        this.template = template;
    }

    public List<ProductEntity> findProducts() {
        return List.of(
                new ProductEntity().setId("1").setName("Smartphone")
                , new ProductEntity().setId("2").setName("Notebook")
        );
    }
}


