package de.philipphauer.blog.unittestkotlin

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignControllerTest {
    @Nested
    inner class GetDesigns {
        @Test
        fun `all fields are included`() {
        }
        @Test
        fun `limit parameter`() {
        }

        @Test
        fun `filter parameter`() {
        }
    }

    @Nested
    inner class DeleteDesign {
        @Test
        fun `design is removed from db`() {
        }
        @Test
        fun `return 404 on invalid id parameter`() {
        }

        @Test
        fun `return 401 if not authorized`() {
        }
    }
}
