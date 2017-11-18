package de.philipphauer.blog

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.philipphauer.blog.util.FunctionsMySQL
import org.assertj.core.api.Assertions.assertThat
import org.h2.jdbcx.JdbcDataSource
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DesignResourceTest {

    private val resource = initDesignResource()

    @Test
    fun `get design without token`() {
        createDesigns(amount = 20)
        val response = resource.getDesigns(Request(Method.GET, "/designs?pageSize=3"))

        println(response)
        assertThat(response.toPageable().nextPage).contains("?continue=TODO")
    }

    //TODO test:
    // page with same key/ts
    // final page (amount < page size and = page size)
    // first element of next page: a) is first one with new key, b) not the same one
    // checksum usage


    private fun initDesignResource(): DesignResource {
        val dao = DesignDAO(dataSource)
        FunctionsMySQL.register(dataSource.connection)
        ScriptUtils.executeSqlScript(dataSource.connection,  ClassPathResource("create-designs-table.sql"))
        return DesignResource(dao)
    }

    private fun createDesigns(amount: Int) {
        val utilTemplate = JdbcTemplate(dataSource)
        val now = Instant.now()
        val values = (0..amount).mapIndexed{ i, _ -> arrayOf(
                "Cat",
                "http://domain.de/cat.jpg",
                now.plusSeconds(i.toLong()).epochSecond
        ) }
        utilTemplate.batchUpdate("INSERT INTO designs (title, imageUrl, dateModified) VALUES (?, ?, FROM_UNIXTIME(?))", values)
    }
}

private val dataSource = JdbcDataSource().apply {
    user = "sa"
    password = ""
    setURL("jdbc:h2:mem:access;MODE=MySQL;DB_CLOSE_DELAY=-1")
}

private val mapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
private fun Response.toPageable() = mapper.readValue(bodyString(), PageableResponse::class.java)

data class PageableResponse(
        val nextPage: String
)
