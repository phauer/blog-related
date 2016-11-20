package de.philipphauer.blog.testingrestservice.integrationtests.dtoKotlin

//definition:
data class BlogDTO (val name: String, val description: String, val url: String)
//getters, constructor, hashCode(), equals(), toString(), copy() are included!

//usage:
val newBlog = BlogDTO(
        name = "Example",
        description = "Example",
        url = "www.blogdomain.de")
//readable due to named arguments
