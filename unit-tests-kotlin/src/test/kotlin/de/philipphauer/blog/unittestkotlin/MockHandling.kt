package de.philipphauer.blog.unittestkotlin

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.TestInstance

// Do:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignControllerTest {

    private val dao: DesignDAO = mock()
    private val mapper: DesignMapper = mock()
    private val controller = DesignController(dao, mapper)

    @BeforeEach
    fun init() {
        reset(dao, mapper)
    }

    // takes 210 ms
    @RepeatedTest(300)
    fun foo() {
        controller.doSomething()
    }
}

//Don't
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignControllerTest_RecreatingMocks {

    private lateinit var dao: DesignDAO
    private lateinit var mapper: DesignMapper
    private lateinit var controller: DesignController

    @BeforeEach
    fun init() {
        dao = mock()
        mapper = mock()
        controller = DesignController(dao, mapper)
    }

    // takes 1,5 s!
    @RepeatedTest(300)
    fun foo() {
        controller.doSomething()
    }
}


open class DesignDAO
open class DesignMapper
class DesignController(val dao: DesignDAO, val mapper: DesignMapper) {
    fun doSomething() {

    }
}
