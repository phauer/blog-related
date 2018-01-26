package de.philipphauer.blog.pagination

import de.philipphauer.blog.pagination.token.ContinuationToken
import de.philipphauer.blog.pagination.token.Page
import de.philipphauer.blog.pagination.token.createPage
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import java.time.Instant
import javax.sql.DataSource


class DesignDAO(dataSource: DataSource) {

    private val template = JdbcTemplate(dataSource)

    fun getDesignsSince(modifiedSince: Instant, pageSize: Int): Page<DesignEntity> {
        val sql = """SELECT * FROM designs
            WHERE dateModified >= FROM_UNIXTIME(${modifiedSince.epochSecond})
            ORDER BY dateModified asc, id asc
            LIMIT $pageSize;"""
        val designs = template.query(sql, this::mapToDesign)
        return createPage(designs, null, pageSize)
    }

    fun getNextDesignPage(token: ContinuationToken, pageSize: Int): Page<DesignEntity> {
        val sql = """SELECT * FROM designs
            WHERE dateModified > FROM_UNIXTIME(${token.timestamp})
            OR (dateModified = FROM_UNIXTIME(${token.timestamp}) AND id > ${token.id})
            ORDER BY dateModified asc, id asc
            LIMIT $pageSize;"""
        val designs = template.query(sql, this::mapToDesign)
        return createPage(designs, token, pageSize)
    }

    private fun mapToDesign(rs: ResultSet, rowNum: Int) = DesignEntity(
        id = rs.getString("id"),
        title = rs.getString("title"),
        imageUrl = rs.getString("imageUrl"),
        dateModified = rs.getTimestamp("dateModified").toInstant()
    )
}
