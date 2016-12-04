package de.philipphauer.blog.testingrestservice.integrationtests.dtoKotlin

import com.jayway.restassured.RestAssured.given
import com.jayway.restassured.builder.RequestSpecBuilder
import com.jayway.restassured.filter.log.RequestLoggingFilter
import com.jayway.restassured.filter.log.ResponseLoggingFilter
import com.jayway.restassured.http.ContentType
import com.jayway.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.BeforeClass
import org.junit.Test

class BlogsKotlinTest {

    companion object{
        private var spec: RequestSpecification? = null

        @JvmStatic
        @BeforeClass
        fun initSpec() {
            spec = RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri("http://localhost:8080/")
                    .addFilter(ResponseLoggingFilter())
                    .addFilter(RequestLoggingFilter())
                    .build()
        }
    }

    @Test
    fun kotlinPower(){
        //it's important to add the jackson-module-kotlin to your project dependencies
        //in order to make the deserialization to the Kotlin object work.
        val retrievedBlog = given()
                .spec(spec)
                .`when`()
                .get("blogs/1")
                .then()
                .statusCode(200)
                .extract().`as`(BlogRetrievalDTO::class.java)
        assertThat(retrievedBlog.name).isNotEmpty()
    }
}