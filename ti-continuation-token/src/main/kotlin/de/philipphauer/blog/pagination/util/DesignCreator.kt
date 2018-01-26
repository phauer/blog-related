package de.philipphauer.blog.pagination.util

import org.springframework.jdbc.core.JdbcTemplate
import java.time.Instant
import javax.sql.DataSource

class DesignCreator(dataSource: DataSource) {

    private val utilTemplate = JdbcTemplate(dataSource)

    fun createDesigns(amount: Int, startDate: Instant = Instant.now()) {
        val values = (1..amount).mapIndexed { i, _ ->
            arrayOf(
                i,
                "Cat $i",
                "http://domain.de/cat$i.jpg",
                startDate.plusSeconds(i.toLong()).epochSecond
            )
        }
        utilTemplate.batchUpdate(
            "INSERT INTO designs (id, title, imageUrl, dateModified) VALUES (?, ?, ?, FROM_UNIXTIME(?))",
            values
        )
    }

    fun insertDesigns(designData: List<Pair<String, Long>>) {
        val values = designData.map { (id, timestamp) ->
            arrayOf(
                id,
                "Cat $id",
                "http://domain.de/cat$id.jpg",
                timestamp
            )
        }
        utilTemplate.batchUpdate(
            "INSERT INTO designs (id, title, imageUrl, dateModified) VALUES (?, ?, ?, FROM_UNIXTIME(?))",
            values
        )
    }

    fun removeAllDesigns() {
        utilTemplate.execute("TRUNCATE TABLE designs;")
    }
}