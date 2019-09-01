package com.phauer.modernunittesting;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public class Product {

    private int id;
    private String name;
    private String category;
    private Instant dateCreated;


    public Product() {

    }

    public Product(int id, String name, String category) {

        this.id = id;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Product setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(dateCreated, product.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateCreated);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
