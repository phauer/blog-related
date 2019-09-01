package com.phauer.modernunittesting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterTest {

    private Calculator calculator = new Calculator();

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "5, 3, 8",
            "10, -20, -10"
    })
    public void add(int summand1, int summand2, int expectedSum) {
        assertThat(calculator.add(summand1, summand2)).isEqualTo(expectedSum);
    }
}

class Calculator {

    public int add(int summand1, int summand2) {
        return summand1 + summand2;
    }
}

