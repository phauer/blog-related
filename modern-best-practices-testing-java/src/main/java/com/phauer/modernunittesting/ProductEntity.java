package com.phauer.modernunittesting;

public class ProductEntity {
    private String id;
    private String name;
    private String category;
    private String description;
    private int stockAmount;

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public ProductEntity setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductEntity setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getId() {
        return id;
    }

    public ProductEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }
}
