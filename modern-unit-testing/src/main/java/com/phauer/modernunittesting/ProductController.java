package com.phauer.modernunittesting;

import org.springframework.stereotype.Component;

@Component
public class ProductController {

    private ProductDAO dao;

    public ProductController(ProductDAO dao, TaxServiceClient client, PriceCalculator calculator) {
        this.dao = dao;
    }
}
