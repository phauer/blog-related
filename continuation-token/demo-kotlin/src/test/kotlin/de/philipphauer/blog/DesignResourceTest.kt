package de.philipphauer.blog

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.philipphauer.blog.util.DesignCreator
import de.philipphauer.blog.util.FunctionsMySQL
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
    private val creator = DesignCreator(dataSource)

    @Test
    fun `happy path`() {
        creator.createDesigns(amount = 10)
        val response = resource.getDesigns(Request(Method.GET, "/designs?pageSize=3"))

        println(response)
//        assertThat(response.toPageable().nextPage).contains("?continue=TODO")
    }

    //TODO test: find better test abstraction. e.g. PaginationTest
    // page with same key/ts
    // final page (amount < page size and = page size)
    // first element of next page: a) is first one with new key, b) not the same one
    // empty result
    // correct page size
    // no `nextPage` in last page
    // checksum usage


    private fun initDesignResource(): DesignResource {
        val dao = DesignDAO(dataSource)
        FunctionsMySQL.register(dataSource.connection)
        ScriptUtils.executeSqlScript(dataSource.connection,  ClassPathResource("create-designs-table.sql"))
        return DesignResource(dao)
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
