package com.phauer.unittestkotlin.mockk


class UserScheduler(
    val client: UserClient,
    val dao: UserDAO
) {
    fun start(id: Int) {
        val user = client.getUser(id)
        dao.saveUser(user)
    }
}

class UserClient {
    fun getUser(id: Int): User {
        println("getClient()")
        return User(id = 99, name = "Albert", age = 30)
    }
}

class UserDAO {
    fun saveUser(user: User) {
        println("saveUser()")
    }
}

data class User(
    val id: Int,
    val name: String,
    val age: Int
)
