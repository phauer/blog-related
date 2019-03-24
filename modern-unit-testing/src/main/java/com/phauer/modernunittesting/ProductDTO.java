package com.phauer.modernunittesting;

import java.util.Objects;
import java.util.StringJoiner;

public class ProductDTO {
    private String id;
    private String name;
    private double price;

    public double getPrice() {
        return price;
    }

    public ProductDTO setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getId() {
        return id;
    }

    public ProductDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductDTO.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("price=" + price)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
