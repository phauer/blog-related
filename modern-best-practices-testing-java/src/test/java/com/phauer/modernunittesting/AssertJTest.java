package com.phauer.modernunittesting;


import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertJTest {

    @Test
    public void bla() {
        var instant1 = Instant.now();
        var instant2 = Instant.now();
        var actualProductList = new ArrayList<Product>();
        actualProductList.add(new Product(1, "Samsung Galaxy", "Smartphone"));
        var actualProduct = new Product(1, "Samsung", "Smartphone");
        var expectedProduct = new Product(2, "iPhone", "Smartphone");
        var expectedProduct1 = new Product();
        var expectedProduct2 = new Product();

        assertThat(actualProductList).containsExactly(
                createProductDTO("1", "Smartphone", 250.00),
                createProductDTO("1", "Smartphone", 250.00)
        );

        assertThat(actualProductList).anySatisfy(product -> {
            assertThat(product.getDateCreated()).isBetween(instant1, instant2);
        });

        assertThat(actualProduct)
                .isEqualToIgnoringGivenFields(expectedProduct, "id");

        assertThat(actualProductList)
                .usingElementComparatorIgnoringFields("id")
                .containsExactly(expectedProduct1, expectedProduct2);

        assertThat(actualProductList)
                .extracting(Product::getId)
                .containsExactly(1, 2);

        // Don't
        assertTrue(actualProductList.contains(expectedProduct));
        assertTrue(actualProductList.size() == 5);
        assertTrue(actualProduct instanceof Product);

        // Do
        assertThat(actualProductList).contains(expectedProduct);
        assertThat(actualProductList).hasSize(5);
        assertThat(actualProduct).isInstanceOf(Product.class);
    }

    private Product createProductDTO(String s, String smartphone, double v) {
        return new Product();
    }
}
