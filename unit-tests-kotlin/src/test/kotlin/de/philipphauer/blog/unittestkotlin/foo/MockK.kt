package de.philipphauer.blog.unittestkotlin.foo

import de.philipphauer.blog.unittestkotlin.DesignController
import de.philipphauer.blog.unittestkotlin.DesignDAO
import de.philipphauer.blog.unittestkotlin.DesignMapper
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.TestInstance

//with MockK, the mocked class can be final! no changes required!

// Do:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignControllerTest_MockK {

    private val dao: DesignDAO = mockk()
    private val mapper: DesignMapper = mockk()
    private val controller = DesignController(dao, mapper)

    @BeforeEach
    fun init() {
        clearMocks(dao, mapper)
    }

    // takes 250 ms
    @RepeatedTest(300)
    fun foo() {
        controller.doSomething()
    }
}

//Don't
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignControllerTest_RecreatingMocks_MockK {

    private lateinit var dao: DesignDAO
    private lateinit var mapper: DesignMapper
    private lateinit var controller: DesignController

    @BeforeEach
    fun init() {
        dao = mockk()
        mapper = mockk()
        controller = DesignController(dao, mapper)
    }

    // takes 2 s! (mockk is even slower (0,5 s) than mockito-kotlin)
    // but this approach is deprecated anyway.
    @RepeatedTest(300)
    fun foo() {
        controller.doSomething()
    }
}

//class DesignDAO
//class DesignMapper
//class DesignController(val dao: DesignDAO, val mapper: DesignMapper) {
//    fun doSomething() {
//
//    }
//}
