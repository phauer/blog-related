package de.philipphauer.blog

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.h2.jdbcx.JdbcDataSource
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ScriptUtils

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DesignResourceTest {

    private val resource = initDesignResource()

    private fun initDesignResource(): DesignResource {
        val dataSource = JdbcDataSource().apply {
            user = "sa"
            password = ""
            setURL("jdbc:h2:mem:access;MODE=MySQL;DB_CLOSE_DELAY=-1")
        }
        val dao = DesignDAO(dataSource)
        ScriptUtils.executeSqlScript(dataSource.connection,  ClassPathResource("create-designs-table.sql"))
        return DesignResource(dao)
    }

    @Test
    fun getDesigns() {
        val response = resource.getDesigns(Request(Method.GET, "/designs"))

        assertThat(response.toPageable().nextPage).contains("?continue=TODO")
    }
}

private val mapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
private fun Response.toPageable() = mapper.readValue(bodyString(), Pageable::class.java)

data class Pageable(
        val nextPage: String
)