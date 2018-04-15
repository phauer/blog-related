package de.philipphauer.blog

import com.mongodb.MongoClientURI
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

class MongoDesignDAO {

    private val dbFactory = SimpleMongoDbFactory(MongoClientURI("mongodb://localhost:27017/test"))
    private val template = MongoTemplate(dbFactory)

    fun findDesigns(): List<Design> = template.findAll(Design::class.java)
}

@Document(collection = "designs")
data class Design(
    val id: ObjectId,
    val name: String,
    val description: String,
    val date: Instant,
    val tags: List<String>,
    val state: String,
    val designer: Designer
)

data class Designer(
    @field:Field("id")
    val id: Int,
    val name: String,
    val address: String
)