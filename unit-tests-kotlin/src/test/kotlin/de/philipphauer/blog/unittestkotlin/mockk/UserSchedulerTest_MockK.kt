package de.philipphauer.blog.unittestkotlin.mockk

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

// Do:
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserSchedulerTest {

    private val dao: UserDAO = mockk(relaxed = true)
    private val client: UserClient = mockk(relaxed = true)
    private val scheduler = UserScheduler(client, dao)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun start() {
        val daoMock: UserDAO = mockk(relaxed = true)
        val clientMock: UserClient = mockk()
        val user = User(id = 1, name = "Ben", age = 29)
        every { clientMock.getUser(any()) } returns user

        val scheduler = UserScheduler(clientMock, daoMock)
        scheduler.start(1)

        verifySequence {
            clientMock.getUser(1)
            daoMock.saveUser(user)
        }
    }

    @Test
    fun bla() {
        val clientMock: UserClient = mockk(relaxed = true)
        println(clientMock.getUser(1).age) // 0

//        val clientMock2: UserClient = mockk()
//        println(clientMock2.getUser(1).age) // exception
    }
}