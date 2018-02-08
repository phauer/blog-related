package de.philipphauer.blog.pagination

import de.philipphauer.blog.pagination.token.ContinuationToken
import de.philipphauer.blog.pagination.token.Page
import de.philipphauer.blog.pagination.token.createPage
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import java.time.Clock
import java.time.Instant
import javax.sql.DataSource


class DesignDAO(
    dataSource: DataSource,
    /** using the timestamp of the application (instead of the database) is only appropriate
     *  if the application also exclusively sets and updates the timestamp */
    private val clock: Clock
) {

    private val template = JdbcTemplate(dataSource)

    fun getDesignsSince(modifiedSince: Instant, pageSize: Int): Page<DesignEntity> {
        val now = clock.instant().epochSecond
        val sql = """SELECT * FROM designs
            WHERE dateModified >= FROM_UNIXTIME(${modifiedSince.epochSecond})
            AND dateModified < FROM_UNIXTIME($now)
            ORDER BY dateModified asc, id asc
            LIMIT $pageSize;"""
        val designs = template.query(sql, this::mapToDesign)
        return createPage(entities = designs, previousToken = null, pageSize = pageSize)
    }

    fun getNextDesignPage(token: ContinuationToken, pageSize: Int): Page<DesignEntity> {
        val now = clock.instant().epochSecond
        val sql = """SELECT * FROM designs
            WHERE (
              dateModified > FROM_UNIXTIME(${token.timestamp})
              OR (dateModified = FROM_UNIXTIME(${token.timestamp}) AND id > ${token.id})
            )
            AND dateModified < FROM_UNIXTIME($now)
            ORDER BY dateModified asc, id asc
            LIMIT $pageSize;"""
        val designs = template.query(sql, this::mapToDesign)
        return createPage(entities = designs, previousToken = token, pageSize = pageSize)
    }

    private fun mapToDesign(rs: ResultSet, rowNum: Int) = DesignEntity(
        id = rs.getString("id"),
        title = rs.getString("title"),
        imageUrl = rs.getString("imageUrl"),
        dateModified = rs.getTimestamp("dateModified").toInstant()
    )
}
