package de.philipphauer.mongojack;

import org.mongojack.ObjectId;

public class Product {
    @ObjectId
    private String id;
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}