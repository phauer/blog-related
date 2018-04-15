package de.philipphauer.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get

class ExternalServiceUserClient {

    private val client = HttpClient(CIO)
    private val mapper = ObjectMapper().registerKotlinModule()

    suspend fun findUsers(): List<UserDTO> {
        val json = client.get<String>("http://localhost:5000/users")
        val userResponse = mapper.readValue(json, UserResponseDTO::class.java)
        return userResponse.users
    }
}

data class UserResponseDTO(
    val size: Int,
    val users: List<UserDTO>
)

data class UserDTO(
    val address: String,
    val company: String,
    val email: String,
    val id: Int,
    val keyAccountInfo: String?,
    val name: String
)
