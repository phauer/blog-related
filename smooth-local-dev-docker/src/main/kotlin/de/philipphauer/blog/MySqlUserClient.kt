package de.philipphauer.blog

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import java.time.Instant

class MySqlUserClient {

    private val dataSource = DataSourceBuilder.create()
        .username("root")
        .password("root")
        .url("jdbc:mysql://localhost:3306/database")
        .driverClassName("com.mysql.cj.jdbc.Driver")
        .build()
    private val template = JdbcTemplate(dataSource)

    fun findUsers() = template.query("SELECT * FROM users;", this::mapToUser)

    private fun mapToUser(rs: ResultSet, rowNum: Int) = User(
        id = rs.getInt("id")
        , name = rs.getString("name")
        , state = State.valueOf(rs.getString("state"))
        , birthday = rs.getTimestamp("birthday").toInstant()
        , notes = rs.getString("notes")
        , adult = rs.getBoolean("is_adult")
    )
}

data class User(
    val id: Int,
    val name: String,
    val state: State,
    val birthday: Instant,
    val notes: String,
    val adult: Boolean
)

enum class State { ACTIVE, INACTIVE }
