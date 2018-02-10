package de.philipphauer.blog.unittestkotlin

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.vaadin.navigator.View
import com.vaadin.ui.Button
import com.vaadin.ui.Panel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignViewTest {

    private val dao: DesignDAO = mock()
    // the class under test has state
    private lateinit var view: DesignView

    @BeforeEach
    fun init() {
        reset(dao)
        view = DesignView(dao)
    }

    @Test
    fun changeButton() {
        assertThat(view.button.caption).isEqualTo("Hi")
        view.changeButton()
        assertThat(view.button.caption).isEqualTo("Hallo")
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