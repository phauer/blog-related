package de.philipphauer.blog.unittestkotlin.assertAllOrSomeFields

import io.kotest.assertions.asClue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AssertAllOrSomeFields {

    private val dao = DesignDAO()

    @Test
    fun `all fields are correctly saved`() {
        // insert design into database

        val expectedDesign = Design(
            name = "cat",
            userId = 10,
            tags = listOf("Cat", "Animal")
        )
        dao.findDesign(1) shouldBe expectedDesign
    }

    @Test
    fun `name and tags of a design are changed`() {
        // change name and tags

        val expectedDesign = Design(
            name = "cat",
            userId = 10,
            tags = listOf("Cat", "Animal")
        )
        dao.findDesign(1) shouldBe expectedDesign
    }

    @Test
    fun `name and tags of a design are changed 2`() {
        // change name and tags

        dao.findDesign(1).asClue {
            it.name shouldBe "Cat"
            it.userId shouldBe 10
        }
    }
}

data class Design(
    val userId: Int,
    val name: String,
    val tags: List<String>
)

class DesignDAO {
    fun findDesign(i: Int): Design {
        TODO()
    }

}