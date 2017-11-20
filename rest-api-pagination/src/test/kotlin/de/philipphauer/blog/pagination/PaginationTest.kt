package de.philipphauer.blog.pagination

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.zip.CRC32

internal class PaginationTest{
    @Test
    fun `happy path`(){
        //TODO
//        val queryAdvice = Pagination.calculateQueryAdvice(token, pageSize)
//        val sql = """SELECT * FROM designs
//            WHERE UNIX_TIMESTAMP(dateModified) >= ${queryAdvice.timestamp}
//            ORDER BY dateModified asc, id asc
//            LIMIT ${queryAdvice.limit};"""
//        val designs = template.query(sql, this::mapToDesign)
//        val nextPage = Pagination.createPage(designs, token)
    }


    @Nested
    inner class `createToken` {
        @Test
        fun `only one entity with highest timestamp`() {
            val pageables = listOf(
                    TestPageable(1),
                    TestPageable(2),
                    TestPageable(3),
                    TestPageable(4)
            )
            val token = Pagination.createTokenForPage(pageables)
            assertThat(token).isEqualTo(ContinuationToken(timestamp = 4, offset = 1, checksum = checksum("4")))
        }
        @Test
        fun `two entities with highest timestamp`() {
            val pageables = listOf(
                    TestPageable(1),
                    TestPageable(2),
                    TestPageable("3", 3),
                    TestPageable("4", 3)
            )
            val token = Pagination.createTokenForPage(pageables)
            assertThat(token).isEqualTo(ContinuationToken(timestamp = 3, offset = 2, checksum = checksum("3", "4")))
        }
        //TODO ContinuationToken should be 0 if last page has reached! this information is required here!
        @Test
        fun `all elements have same timestamp`() {
            val pageables = listOf(
                    TestPageable("1",1),
                    TestPageable("2",1),
                    TestPageable("3",1)
            )
            val token = Pagination.createTokenForPage(pageables)
            assertThat(token).isEqualTo(ContinuationToken(timestamp = 1, offset = 3, checksum = checksum("1", "2", "3")))
        }
        @Test
        fun `one element list`() {
            val pageables = listOf(
                    TestPageable(1)
            )
            val token = Pagination.createTokenForPage(pageables)
            assertThat(token).isEqualTo(ContinuationToken(timestamp = 1, offset = 1, checksum = checksum("1")))
        }
        @Test
        fun `empty list`() {
            val token = Pagination.createTokenForPage(listOf())
            assertThat(token).isNull()
        }
        private fun checksum(vararg ids: String): Long{
            val hash = CRC32()
            hash.update(ids.joinToString("_").toByteArray())
            return hash.value
        }
    }

    @Nested
    inner class `calculateQueryAdvice`{
        @Test
        fun `no token provided`(){
            val advice = Pagination.calculateQueryAdvice(token = null, pageSize = 5)
            assertThat(advice).isEqualTo(QueryAdvice(timestamp = 0, limit = 5))
        }
        @Test
        fun `there was one element with timestamp 20 in the last page`(){
            val token = ContinuationToken(timestamp = 20, offset = 1, checksum = 999)
            val advice = Pagination.calculateQueryAdvice(token, pageSize = 5)
            assertThat(advice).isEqualTo(QueryAdvice(timestamp = 20, limit = 6))
        }
        @Test
        fun `there were 3 elements with timestamp 20 in the last page`(){
            val token = ContinuationToken(timestamp = 20, offset = 3, checksum = 999)
            val advice = Pagination.calculateQueryAdvice(token, pageSize = 5)
            assertThat(advice).isEqualTo(QueryAdvice(timestamp = 20, limit = 8))
        }
    }

    @Nested
    inner class `getEntitiesWithHighestKey`{
        @Test
        fun `all have different keys`(){
            val pageables = listOf(
                    TestPageable(1),
                    TestPageable(2),
                    TestPageable(3)
            )
            val entities = Pagination.getEntitiesWithHighestKey(pageables)
            assertThat(entities).containsExactly(TestPageable(3))
        }
        @Test
        fun `some with the same key`(){
            val pageables = listOf(
                    TestPageable(1),
                    TestPageable(2),
                    TestPageable("4",3),
                    TestPageable("5",3)
                    )
            val entities = Pagination.getEntitiesWithHighestKey(pageables)
            assertThat(entities).containsExactly(TestPageable("4",3), TestPageable("5",3))
        }
        @Test
        fun `all with the same key`(){
            val pageables = listOf(
                    TestPageable("1",1),
                    TestPageable("2",1),
                    TestPageable("3",1)
            )
            val entities = Pagination.getEntitiesWithHighestKey(pageables)
            assertThat(entities).containsExactly(TestPageable("1",1), TestPageable("2",1), TestPageable("3",1))
        }
        @Test
        fun `empty list`(){
            val entities = Pagination.getEntitiesWithHighestKey(listOf())
            assertThat(entities).isEmpty()
        }
        @Test
        fun `only one element`(){
            val pageables = listOf(TestPageable(1))
            val entities = Pagination.getEntitiesWithHighestKey(pageables)
            assertThat(entities).containsExactly(TestPageable("1",1))
        }
    }
}

data class TestPageable(
        val id: String,
        val timestamp: Long
): Pageable {
    constructor(timestamp: Long): this(timestamp.toString(), timestamp)
    override fun getIdentifier() = id
    override fun getKey() = timestamp
}