package com.phauer.modernunittesting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private ProductDAO dao;

    public ProductController(ProductDAO dao, TaxServiceClient client, PriceCalculator calculator) {
        this.dao = dao;
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        List<ProductEntity> products = dao.findProducts();
        return toDto(products);
    }

    private List<ProductDTO> toDto(List<ProductEntity> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductDTO toDto(ProductEntity entity) {
        return new ProductDTO()
                .setId(entity.getId())
                .setName(entity.getName());
    }
}
