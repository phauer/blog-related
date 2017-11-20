package de.philipphauer.blog

import de.philipphauer.blog.pagination.ContinuationToken
import de.philipphauer.blog.pagination.Pagination
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import javax.sql.DataSource


class DesignDAO(dataSource: DataSource){

    private val template = JdbcTemplate(dataSource)

    fun getDesigns(token: ContinuationToken?, pageSize: Int): DesignPageEntity {
        val queryAdvice = Pagination.calculateQueryAdvice(token, pageSize)
        val sql = """SELECT * FROM designs
            WHERE UNIX_TIMESTAMP(dateModified) >= ${queryAdvice.timestamp}
            ORDER BY dateModified asc, id asc
            LIMIT ${queryAdvice.limit};"""
        val designs = template.query(sql, this::mapToDesign)
        val nextPage = Pagination.createPage(designs, token)
        return DesignPageEntity(nextPage.entities as List<DesignEntity>, nextPage.currentToken)
    }

    private fun mapToDesign(rs: ResultSet, rowNum: Int) = DesignEntity(
            id = rs.getString("id"),
            title = rs.getString("title"),
            imageUrl = rs.getString("imageUrl"),
            dateModified = rs.getTimestamp("dateModified").toInstant()
    )

}

data class DesignPageEntity(
        val designs: List<DesignEntity>,
        val token: ContinuationToken?
)
