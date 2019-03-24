package com.phauer.modernunittesting;

import java.time.Instant;
import java.util.Objects;

public class Product {

    private String id;
    private Instant dateCreated;

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
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
}
