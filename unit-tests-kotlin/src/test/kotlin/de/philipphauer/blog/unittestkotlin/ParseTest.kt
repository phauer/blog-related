package de.philipphauer.blog.unittestkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParseTest {

    @Test
    fun `parse valid tokens 1`() {
        assertThat(parse("1511443755_2")).isEqualTo(Token(1511443755, "2"))
        assertThat(parse("151175_13521")).isEqualTo(Token(151175, "13521"))
        assertThat(parse("151144375_id")).isEqualTo(Token(151144375, "id"))
        assertThat(parse("15114437599_1")).isEqualTo(Token(15114437599, "1"))
        assertThat(parse(null)).isEqualTo(null)
    }

    @Test
    fun `parse valid tokens 2`() {
        assertThat(parse("1511443755_2")).isEqualTo(Token(1511443755, "2"))
        assertThat(parse("151175_13521")).isEqualTo(Token(151175, "13521"))
        assertThat(parse("151144375_id")).isEqualTo(Token(151144375, "id"))
        assertThat(parse("15114437599_1")).isEqualTo(Token(15114437599, "1"))
        assertThat(parse(null)).isEqualTo(null)
    }

    @ParameterizedTest
    @MethodSource("validTokenProvider")
    fun `parse valid tokens`(data: TestData) {
        assertThat(parse(data.input)).isEqualTo(data.expected)
    }

    private fun validTokenProvider() = Stream.of(
        TestData(input = "1511443755_2", expected = Token(1511443755, "2")),
        TestData(input = "151175_13521", expected = Token(151175, "13521")),
        TestData(input = "151144375_id", expected = Token(151144375, "id")),
        TestData(input = "15114437599_1", expected = Token(15114437599, "2")),
        TestData(input = null, expected = null)
    )
}

data class TestData(
    val input: String?,
    val expected: Token?
)

fun parse(value: String?): Token? {
    value ?: return null
    val parts = value.split("_")
    if (parts.size != 2) {
        throw IllegalArgumentException(value, null)
    }
    try {
        val timestamp = java.lang.Long.parseUnsignedLong(parts[0])
        val id = parts[1]
        return Token(timestamp, id)
    } catch (ex: Exception) {
        throw IllegalArgumentException(value, ex)
    }
}

data class Token(
    val timestamp: Long,
    val id: String
)