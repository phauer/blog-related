package de.philipphauer.blog.unittestkotlin

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagClientTest {
    @Test
    fun `basic tag list`() {
    }

    @Test
    fun `empty tag list`() {
    }

    @Test
    fun `empty tag translations`() {
    }

    @Nested
    inner class ErrorCases {
        @Test
        fun `server sends empty body`() {
        }

        @Test
        fun `server sends invalid json`() {
        }

        @Test
        fun `server sends 500`() {
        }

        @Test
        fun `timeout - server response takes too long`() {
        }

        @Test
        fun `not available at all - wrong url`() {
        }
    }
}
