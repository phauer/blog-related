package de.philipphauer.blog

import de.philipphauer.blog.pagination.ContinuationToken
import de.philipphauer.blog.pagination.Pagination
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.UnsupportedOperationException
import java.sql.ResultSet
import javax.sql.DataSource


class DesignDAO(dataSource: DataSource){

    private val template = JdbcTemplate(dataSource)

    fun getDesigns(token: ContinuationToken?, pageSize: Int): DAOResponse {
        if (token == null){
            val designs = template.query("select * from designs order by dateModified asc, id asc limit $pageSize;", this::mapToDesign)
            val newToken = Pagination.createToken(designs)
            return DAOResponse(designs, newToken)
        } else {
            throw UnsupportedOperationException("not yet implemented!")
        }
    }

    fun mapToDesign(rs: ResultSet, rowNum: Int) = Design(
            id = rs.getString("id"),
            title = rs.getString("title"),
            imageUrl = rs.getString("imageUrl"),
            dateModified = rs.getTimestamp("dateModified").toInstant()
    )

}

data class DAOResponse(
        val designs: List<Design>,
        val token: ContinuationToken
)
