package de.philipphauer.blog.pagination

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ContinuationTokenParserTest {

    private val parser = ContinuationTokenParser

    @Test
    fun valid() {
        assertThat(parser.parse("1511443755:2:1842515611"))
                .isEqualTo(ContinuationToken(timestamp = 1511443755, offset = 2, checksum = 1842515611))
        assertThat(parser.parse("1511443755:1:1842521611"))
                .isEqualTo(ContinuationToken(timestamp = 1511443755, offset = 1, checksum = 1842521611))

        //also support timestamps with millisecond precision
        assertThat(parser.parse("1511443755999:1:1842521611"))
                .isEqualTo(ContinuationToken(timestamp = 1511443755999, offset = 1, checksum = 1842521611))
    }

    @ParameterizedTest
    @MethodSource("invalidTokenProvider")
    fun invalid(invalidToken: String) {
        val exception = assertThrows(ContinuationTokenParseException::class.java){
            parser.parse(invalidToken)
        }
        assertThat(exception.message).isEqualTo("Invalid token '$invalidToken'.")
    }

    private fun invalidTokenProvider(): Stream<String> = Stream.of(
            "asdf:1:1842521611"
            , "1511443755:sadfasd:1842521611"
            , "1511443755:1:sadfasd"
            , "1511443755:1"
            , "1511443755:1:"
            , ""
            , "::"
            , "12::"
            , "12::213"
            , ":1231:213"
    )
}
