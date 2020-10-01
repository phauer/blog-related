package com.phauer.unittestkotlin

import com.vaadin.navigator.View
import com.vaadin.ui.Button
import com.vaadin.ui.Panel
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignViewTest {

    private val dao: DesignDAO = mockk()

    // the class under test has state
    private lateinit var view: DesignView

    @BeforeEach
    fun init() {
        clearAllMocks()
        view = DesignView(dao)
    }

    @Test
    fun changeButton() {
        view.button.caption shouldBe "Hi"
        view.changeButton()
        view.button.caption shouldBe "Hallo"
    }
}

// class with state
class DesignView(val dao: DesignDAO) : Panel(), View {

    val button = Button("Hi")

    init {
        content = button
    }

    fun changeButton() {
        button.caption = "Hallo"
    }
}