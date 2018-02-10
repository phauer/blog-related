package de.philipphauer.blog.unittestkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParseTest {

    @ParameterizedTest
    @MethodSource("validTokenProvider")
    fun `parse valid tokens`(data: ValidTokenTestData) {
        assertThat(parse(data.value)).isEqualTo(data.expectedToken)
    }

    private fun validTokenProvider() = Stream.of(
        ValidTokenTestData(value = "1511443755_2", expectedToken = Token(1511443755, "2"))
        , ValidTokenTestData(value = "1463997600_10273521", expectedToken = Token(1463997600, "10273521"))
        , ValidTokenTestData(value = "151144375_1", expectedToken = Token(151144375, "1"))
        , ValidTokenTestData(value = "151144375_id", expectedToken = Token(151144375, "id"))
        , ValidTokenTestData(value = "1511443755999_1", expectedToken = Token(1511443755999, "1"))
        , ValidTokenTestData(value = null, expectedToken = null)
    )
}

data class ValidTokenTestData(
    val value: String?,
    val expectedToken: Token?
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