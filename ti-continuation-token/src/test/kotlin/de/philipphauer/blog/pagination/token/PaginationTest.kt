package de.philipphauer.blog.pagination.token

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PaginationTest {

    @ParameterizedTest
    @MethodSource("validTokenProvider")
    fun parse_valid(data: ValidTokenTestData) {
        assertThat(data.token.toContinuationToken()).isEqualTo(data.continuationToken)
    }

    private fun validTokenProvider() = Stream.of(
        ValidTokenTestData("1511443755_2", ContinuationToken(1511443755, "2"))
        , ValidTokenTestData("1463997600_10273521", ContinuationToken(1463997600, "10273521"))
        , ValidTokenTestData("151144375_1", ContinuationToken(151144375, "1"))
        // id can also be strings
        , ValidTokenTestData("151144375_id", ContinuationToken(151144375, "id"))
        // timestamp can also have millisecond precision
        , ValidTokenTestData("1511443755999_1", ContinuationToken(1511443755999, "1"))
        , ValidTokenTestData(null, null)
    )

    @ParameterizedTest
    @ValueSource(
        strings = [
            "asdf_1_1842521611"
            , "asdf_1"
            , "1511443755_sadfasd_1842521611"
            , "1511443755_1_sadfasd"
            , ""
            , "__"
            , "12__"
            , "12__213"
            , "_1231_213"
            , "-1231_213"
            , "-2_23"
        ]
    )
    fun parse_invalid(invalidToken: String) {
        assertThatThrownBy { invalidToken.toContinuationToken() }
            .isInstanceOf(InvalidContinuationTokenException::class.java)
            .hasMessageStartingWith("Invalid token '$invalidToken'")
    }

    @Test
    fun onlyOnePage() {
        val entities = listOf(
            TestPageable("1", 10),
            TestPageable("2", 20),
            TestPageable("3", 30)
        )

        val actualPage = createPage(entities, null, 10)

        val expectedPage = Page(
            entities = entities,
            token = ContinuationToken(30, "3"),
            hasNext = false
        )
        assertThat(actualPage).isEqualTo(expectedPage)
    }

    @Test
    fun hasNextPage() {
        val entities = listOf(
            TestPageable("1", 10),
            TestPageable("2", 20),
            TestPageable("3", 30)
        )

        val actualPage = createPage(entities, null, 3)

        val expectedPage = Page(
            entities = entities,
            token = ContinuationToken(30, "3"),
            hasNext = true
        )
        assertThat(actualPage).isEqualTo(expectedPage)
    }

    @Test
    fun onlyOneElement() {
        val entities = listOf(TestPageable("1", 10))

        val actualPage = createPage(entities, null, 3)

        val expectedPage = Page(
            entities = entities,
            token = ContinuationToken(10, "1"),
            hasNext = false
        )
        assertThat(actualPage).isEqualTo(expectedPage)
    }

    @Test
    fun emptyPage() {
        val entities = listOf<TestPageable>()

        val actualPage = createPage(entities, null, 3)

        val expectedPage = Page(entities, null, false)
        assertThat(actualPage).isEqualTo(expectedPage)
    }

    @Test
    fun emptyPage_returnReceivedTokenInCaseOfEmptyPage() {
        val entities = listOf<TestPageable>()

        val token = ContinuationToken(30, "3")
        val actualPage = createPage(entities, token, 3)

        val expectedPage = Page(entities, token, false)
        assertThat(actualPage).isEqualTo(expectedPage)
    }

    @Test
    fun returnANewTokenEvenIfAnTokenIsPassed() {
        val entities = listOf(
            TestPageable("4", 40),
            TestPageable("5", 50),
            TestPageable("6", 60)
        )

        val actualPage = createPage(
            entities,
            ContinuationToken(30, "3"),
            10
        )

        val expectedPage = Page(
            entities = entities,
            token = ContinuationToken(60, "6"),
            hasNext = false
        )
        assertThat(actualPage).isEqualTo(expectedPage)
    }

}

data class TestPageable(
    override val id: String,
    override val timestamp: Long
) : Pageable

data class ValidTokenTestData(
    val token: String?,
    val continuationToken: ContinuationToken?
)