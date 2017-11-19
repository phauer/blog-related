package de.philipphauer.blog.pagination

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
}

data class TestPageable(
        val id: String,
        val dateModified: Long
): Pageable {
    override fun getIdentifier() = id
    override fun getKey() = dateModified
}