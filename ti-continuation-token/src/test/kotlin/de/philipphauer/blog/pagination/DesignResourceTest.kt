package de.philipphauer.blog.pagination

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import de.philipphauer.blog.pagination.util.DesignDatabaseUtil
import de.philipphauer.blog.pagination.util.FunctionsMySQL
import org.assertj.core.api.Assertions.assertThat
import org.h2.jdbcx.JdbcDataSource
import org.http4k.core.Method
import org.http4k.core.Request
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ScriptUtils
import java.time.Clock
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DesignResourceTest {

    private val util = DesignDatabaseUtil(dataSource)

    init {
        FunctionsMySQL.register(dataSource.connection)
        ScriptUtils.executeSqlScript(dataSource.connection, ClassPathResource("create-designs-table.sql"))
    }

    @Test
    fun `page through two pages`() {
        val resource = createDesignResource()
        val startDate = Instant.ofEpochSecond(1512757070)
        util.createDesigns(amount = 5, startDate = startDate)

        val firstPageResponse = resource.getDesigns(Request(Method.GET, "/designs?pageSize=3"))

        val firstPage = firstPageResponse.toPageDTO()
        assertThat(firstPage.continuationToken).isNotNull()
        assertThat(firstPage.hasNext).isTrue()
        assertThat(firstPage.nextPage).isNotNull()
        assertThat(firstPage.designs).containsExactly(
            DesignDTO(id = "0", title = "Cat 0", imageUrl = "http://domain.de/cat0.jpg", dateModified = 1512757070),
            DesignDTO(id = "1", title = "Cat 1", imageUrl = "http://domain.de/cat1.jpg", dateModified = 1512757071),
            DesignDTO(id = "2", title = "Cat 2", imageUrl = "http://domain.de/cat2.jpg", dateModified = 1512757072)
        )

        val secondPageResponse = resource.getDesigns(Request(Method.GET, firstPage.nextPage!!))
        val secondPage = secondPageResponse.toPageDTO()
        assertThat(secondPage.continuationToken).isNotNull()
        assertThat(secondPage.hasNext).isFalse()
        assertThat(secondPage.nextPage).isNotNull()
        assertThat(secondPage.designs).containsExactly(
            DesignDTO(id = "3", title = "Cat 3", imageUrl = "http://domain.de/cat3.jpg", dateModified = 1512757073),
            DesignDTO(id = "4", title = "Cat 4", imageUrl = "http://domain.de/cat4.jpg", dateModified = 1512757074)
        )
    }

    @Test
    fun `start with a certain modifiedSince query parameter and page through two pages`() {
        val resource = createDesignResource()
        val modifiedSince: Long = 1512757072
        val designData = listOf<Pair<String, Long>>(
            "0" to 1512757070
            , "1" to 1512757071
            , "2" to modifiedSince
            , "3" to 1512757073
            , "4" to 1512757074
            , "5" to 1512757075
        )
        util.insertDesigns(designData)

        val firstPageResponse =
            resource.getDesigns(Request(Method.GET, "/designs?modifiedSince=$modifiedSince&pageSize=3"))
        val firstPage = firstPageResponse.toPageDTO()
        assertThat(firstPage.continuationToken).isNotNull()
        assertThat(firstPage.hasNext).isTrue()
        assertThat(firstPage.nextPage).isNotNull()
        assertThat(firstPage.designs).containsExactly(
            DesignDTO(id = "2", title = "Cat 2", imageUrl = "http://domain.de/cat2.jpg", dateModified = 1512757072),
            DesignDTO(id = "3", title = "Cat 3", imageUrl = "http://domain.de/cat3.jpg", dateModified = 1512757073),
            DesignDTO(id = "4", title = "Cat 4", imageUrl = "http://domain.de/cat4.jpg", dateModified = 1512757074)
        )

        val secondPageResponse = resource.getDesigns(Request(Method.GET, firstPage.nextPage!!))
        val secondPage = secondPageResponse.toPageDTO()
        assertThat(secondPage.continuationToken).isNotNull()
        assertThat(secondPage.hasNext).isFalse()
        assertThat(secondPage.nextPage).isNotNull()
        assertThat(secondPage.designs).containsExactly(
            DesignDTO(id = "5", title = "Cat 5", imageUrl = "http://domain.de/cat5.jpg", dateModified = 1512757075)
        )
    }

    /**
     * we miss an element when the following three things are happening within a single second (and given that this is the current second):
    a) element 3's timestamp is set to now (99),
    b) the last page with element 3 is returned (token `3_99`) and
    c) element 2's timestamp is also set to now (99).
    (given a timestamp column with second precision. with ms precision, this three things have to happen within one ms)
    solution: add condition `AND timestamp > now()` to the where clause.
     */
    @Test
    fun `dont miss elements when updates are happening before the last page`() {
        val serverClock = mock<Clock>()
        val resource = createDesignResource(serverClock)
        val designData = listOf<Pair<String, Long>>(
            "1" to 10
            , "2" to 20
            , "3" to 30
        )
        util.insertDesigns(designData)
        val client = PaginationClient(resource = resource, pageSize = 3)

        val sameSecond = Instant.ofEpochSecond(99)
        util.update(id = "3", now = sameSecond)
        whenever(serverClock.instant()).doReturn(sameSecond) //to override the server's "now()".
        client.retrieveNextPageAndRememberResult()
        util.update(id = "2", now = sameSecond) //this must not be missed in the next request
        //simulate a passed second (no Thread.sleep() in test required). in reality, this would be a new pagination run
        whenever(serverClock.instant()).doReturn(sameSecond.plusSeconds(1))
        client.retrieveNextPageAndRememberResult()

        val allDesigns = client.getAllRetrievedDesigns()
        assertThat(allDesigns).containsOnly(
            DesignDTO(id = "1", title = "Cat 1", imageUrl = "http://domain.de/cat1.jpg", dateModified = 10)
            , DesignDTO(id = "2", title = "Cat 2", imageUrl = "http://domain.de/cat2.jpg", dateModified = 20)
            , DesignDTO(id = "3", title = "Cat 3 (UPDATED)", imageUrl = "http://domain.de/cat3.jpg", dateModified = sameSecond.epochSecond)
            //this must not be missed:
            , DesignDTO(id = "2", title = "Cat 2 (UPDATED)", imageUrl = "http://domain.de/cat2.jpg", dateModified = sameSecond.epochSecond)
        )
    }

    @Test
    fun `dont return elements with current timestamp - request with touched since`() {
        val serverClock = mock<Clock>()
        val resource = createDesignResource(serverClock)
        val sameSecond = Instant.ofEpochSecond(99)
        val designData = listOf<Pair<String, Long>>(
            "1" to 10
            , "2" to 20
            , "3" to sameSecond.epochSecond
        )
        util.insertDesigns(designData)

        whenever(serverClock.instant()).doReturn(sameSecond)
        val response = resource.getDesigns(Request(Method.GET, "/designs?pageSize=3&touchedSince=0")).toPageDTO()

        assertThat(response.continuationToken).isEqualTo("20_2")
        assertThat(response.designs).containsOnly(
            DesignDTO(id = "1", title = "Cat 1", imageUrl = "http://domain.de/cat1.jpg", dateModified = 10)
            , DesignDTO(id = "2", title = "Cat 2", imageUrl = "http://domain.de/cat2.jpg", dateModified = 20)
        )
    }

    @Test
    fun `dont return elements with current timestamp - request with token`() {
        val serverClock = mock<Clock>()
        val resource = createDesignResource(serverClock)
        val sameSecond = Instant.ofEpochSecond(99)
        val designData = listOf<Pair<String, Long>>(
            "1" to 10
            , "2" to 20
            , "3" to sameSecond.epochSecond
        )
        util.insertDesigns(designData)

        whenever(serverClock.instant()).doReturn(sameSecond)
        val response = resource.getDesigns(Request(Method.GET, "/designs?pageSize=3&continuationToken=1_10")).toPageDTO()

        assertThat(response.continuationToken).isEqualTo("20_2")
        assertThat(response.designs).containsOnly(
            DesignDTO(id = "1", title = "Cat 1", imageUrl = "http://domain.de/cat1.jpg", dateModified = 10)
            , DesignDTO(id = "2", title = "Cat 2", imageUrl = "http://domain.de/cat2.jpg", dateModified = 20)
        )
    }

    @BeforeEach
    fun cleanup() {
        util.removeAllDesigns()
    }

    private fun createDesignResource(clock: Clock = Clock.systemUTC()): DesignResource {
        val dao = DesignDAO(dataSource, clock)
        return DesignResource(dao)
    }
}

private val dataSource = JdbcDataSource().apply {
    user = "sa"
    password = ""
    setURL("jdbc:h2:mem:access;MODE=MySQL;DB_CLOSE_DELAY=-1")
}

