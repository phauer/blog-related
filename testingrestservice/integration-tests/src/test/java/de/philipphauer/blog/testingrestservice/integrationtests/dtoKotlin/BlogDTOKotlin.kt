package de.philipphauer.blog.testingrestservice.integrationtests.dtoKotlin

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//definition:
data class BlogDTO (val name: String, val description: String, val url: String)
//getters, constructor, hashCode(), equals(), toString(), copy() are included!

//usage:
val newBlog = BlogDTO(
        name = "Example",
        description = "Example",
        url = "www.blogdomain.de")
//readable due to named arguments

//let's use different DTOs for creation and retrieval, because they differ (id!)
@JsonIgnoreProperties(ignoreUnknown = true)
data class BlogRetrievalDTO (val name: String, val description: String, val url: String)