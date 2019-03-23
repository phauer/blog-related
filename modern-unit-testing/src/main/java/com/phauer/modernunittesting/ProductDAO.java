package com.phauer.modernunittesting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDAO {

    public ProductDAO(@Value("jdbcUrl") String jdbcUrl) {

    }

    public List<ProductEntity> findProducts() {
        return List.of(
                new ProductEntity().setId("1").setName("Smartphone")
                , new ProductEntity().setId("2").setName("Notebook")
        );
    }
}


