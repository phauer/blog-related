package com.phauer.unittestkotlin

import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test


class TestSpecificExtFunctions {

    // Don't
    @Test
    fun bla() {
        val taxRate1 = 0.3f
        val taxRate2 = 0.2f
        val taxRate3 = 0.5f

        taxRate1 shouldBe 0.3f.plusOrMinus(0.001f)
        taxRate2 shouldBe 0.2f.plusOrMinus(0.001f)
        taxRate3 shouldBe 0.5f.plusOrMinus(0.001f)
    }

    // Do
    @Test
    fun bla2() {
        val taxRate1 = 0.3f
        val taxRate2 = 0.2f
        val taxRate3 = 0.5f

        taxRate1 shouldBeCloseTo 0.3f
        taxRate2 shouldBeCloseTo 0.2f
        taxRate3 shouldBeCloseTo 0.5f
    }

    private infix fun Float.shouldBeCloseTo(expected: Float) = this shouldBe expected.plusOrMinus(0.001f)
}