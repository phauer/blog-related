package com.phauer.unittestkotlin

//import com.nhaarman.mockito_kotlin.mock
//import com.nhaarman.mockito_kotlin.reset
//import com.nhaarman.mockito_kotlin.whenever
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.BeforeClass
//import org.junit.Test
//
//class UserControllerTest {
//    companion object {
//        @JvmStatic private lateinit var controller: UserController
//        @JvmStatic private lateinit var repo: UserRepository
//        @BeforeClass @JvmStatic fun initialize() {
//            repo = mock()
//            controller = UserController(repo)
//        }
//    }
//    @Test
//    fun findUser_UserFoundAndHasCorrectValues() {
//        `when`((repo.findUser(1))).thenReturn(User(1, "Peter"))
//        val user = controller.getUser(1)
//        assertEquals(user?.name, "Peter")
//    }
//    @Before
//    fun clear(){
//        reset(repo)
//    }
//}

open class UserRepository {
    open fun findUser(id: Int): User? {
        return User(id, "Peter")
    }
}

class UserController(
    val repo: UserRepository
) {
    fun getUser(id: Int): User? {
        return repo.findUser(id)
    }
}

data class User(
    val id: Int,
    val name: String
)