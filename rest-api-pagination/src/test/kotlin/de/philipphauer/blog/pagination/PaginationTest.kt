package de.philipphauer.blog.pagination

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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
//        val nextPage = Pagination.createNextPage(designs, token)
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
            val pageables = listOf<TestPageable>()
            val entities = Pagination.getEntitiesWithHighestKey(pageables)
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