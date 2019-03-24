package com.phauer.modernunittesting;


import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertJTest {

    @Test
    public void bla(){
        var instant1 = Instant.now();
        var instant2 = Instant.now();
        var actualProductList = new ArrayList<Product>();
        var actualProduct = new Product();
        var expectedProduct = new Product();
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
                .containsExactly("1", "2");
    }

    private Product createProductDTO(String s, String smartphone, double v) {
        return new Product();
    }
}
