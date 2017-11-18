package de.philipphauer.blog

import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import javax.sql.DataSource


class DesignDAO(dataSource: DataSource){

    private val template = JdbcTemplate(dataSource)

    fun getDesigns(): List<Design>{
        return template.query("SELECT * from designs;", this::mapToDesign)
    }

    fun mapToDesign(rs: ResultSet, rowNum: Int) = Design(
            id = rs.getInt("id"),
            title = rs.getString("title"),
            imageUrl = rs.getString("imageUrl")
    )

}