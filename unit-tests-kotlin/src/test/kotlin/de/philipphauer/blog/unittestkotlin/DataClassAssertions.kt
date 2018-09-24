package de.philipphauer.blog.unittestkotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

class DataClassAssertions {

    //Don't
    @Test
    fun test() {
        val client = DesignClient()

        val actualDesign = client.requestDesign(id = 1)

        assertThat(actualDesign.id).isEqualTo(2)
        assertThat(actualDesign.userId).isEqualTo(9)
        assertThat(actualDesign.name).isEqualTo("Cat")
        assertThat(actualDesign.dateCreated).isEqualTo(Instant.ofEpochSecond(1518278198))

        /*
        org.junit.ComparisonFailure: expected:<[2]> but was:<[1]>
        Expected :2
        Actual   :1
         */
    }

    //Do
    @Test
    fun test2() {
        val client = DesignClient()

        val actualDesign = client.requestDesign(id = 1)

        val expectedDesign = Design(
            id = 2,
            userId = 9,
            name = "Cat",
            dateCreated = Instant.ofEpochSecond(1518278198)
        )
        assertThat(actualDesign).isEqualTo(expectedDesign)

        /*
        org.junit.ComparisonFailure: expected:<Design(id=[2], userId=9, name=Cat...> but was:<Design(id=[1], userId=9, name=Cat...>
        Expected :Design(id=2, userId=9, name=Cat, dateCreated=2018-02-10T15:56:38Z)
        Actual   :Design(id=1, userId=9, name=Cat, dateCreated=2018-02-10T15:56:38Z)
         */
    }

    //Do
    @Test
    fun lists() {
        val client = DesignClient()

        val actualDesigns = client.getAllDesigns()

        assertThat(actualDesigns).containsExactly(
            Design(
                id = 1,
                userId = 9,
                name = "Cat",
                dateCreated = Instant.ofEpochSecond(1518278198)
            ),
            Design(
                id = 2,
                userId = 4,
                name = "Dogggg",
                dateCreated = Instant.ofEpochSecond(1518279000)
            )
        )
    }

    @Test
    fun sophisticatedAssertions_single() {
        val client = DesignClient()

        val actualDesign = client.requestDesign(id = 1)

        val expectedDesign = Design(
            id = 2,
            userId = 9,
            name = "Cat",
            dateCreated = Instant.ofEpochSecond(1518278198)
        )
        assertThat(actualDesign).isEqualToIgnoringGivenFields(expectedDesign, "dateCreated")
        assertThat(actualDesign).isEqualToComparingOnlyGivenFields(expectedDesign, "id", "name")
    }


    @Test
    fun sophisticatedAssertions_lists() {
        val client = DesignClient()

        val actualDesigns = client.getAllDesigns()

        assertThat(actualDesigns).usingElementComparatorIgnoringFields("dateCreated").containsExactly(
            Design(
                id = 1,
                userId = 9,
                name = "Cat",
                dateCreated = Instant.ofEpochSecond(1518278198)
            ),
            Design(
                id = 2,
                userId = 4,
                name = "Dogggg",
                dateCreated = Instant.ofEpochSecond(1518279000)
            )
        )
        assertThat(actualDesigns).usingElementComparatorOnFields("id", "name").containsExactly(
            Design(
                id = 1,
                userId = 9,
                name = "Cat",
                dateCreated = Instant.ofEpochSecond(1518278198)
            ),
            Design(
                id = 2,
                userId = 4,
                name = "Dogggg",
                dateCreated = Instant.ofEpochSecond(1518279000)
            )
        )
    }
}

data class Design(
    val id: Int,
    val userId: Int,
    val name: String,
    val dateCreated: Instant
)

class DesignClient {
    fun requestDesign(id: Int) =
        Design(id = 1, userId = 9, name = "Cat", dateCreated = Instant.ofEpochSecond(1518278198))

    fun getAllDesigns() = listOf(
        Design(id = 1, userId = 9, name = "Cat", dateCreated = Instant.ofEpochSecond(1518278198)),
        Design(id = 2, userId = 4, name = "Dog", dateCreated = Instant.ofEpochSecond(1518279000))
    )
}