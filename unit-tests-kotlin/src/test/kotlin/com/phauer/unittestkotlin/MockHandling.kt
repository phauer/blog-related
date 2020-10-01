package com.phauer.unittestkotlin

import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.TestInstance

// Do:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DesignControllerTest_Mock {

    private val dao: DesignDAO = mockk()
    private val mapper: DesignMapper = mockk()
    private val controller = DesignController(dao, mapper)

    @BeforeEach
    fun init() {
        clearAllMocks()
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
        dao = mockk()
        mapper = mockk()
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
