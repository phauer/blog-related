package de.philipphauer.blog.pagination

import org.http4k.core.Method
import org.http4k.core.Request

class PaginationClient(
    private val pageSize: Int,
    private val resource: DesignResource
) {
    private val retrievedDesigns = mutableListOf<DesignDTO>()
    private var nextContinuationToken: String? = null
    private var requestCounter = 0

    fun retrieveNextPageAndRememberResult() {
        val page = if (nextContinuationToken == null) {
            println("Request ${requestCounter++}: Without token")
            resource.getDesigns(Request(Method.GET, "/designs?pageSize=$pageSize")).toPageDTO()
        } else {
            println("Request ${requestCounter++}: With token $nextContinuationToken")
            resource.getDesigns(Request(Method.GET, "/designs?pageSize=$pageSize&continuationToken=$nextContinuationToken")).toPageDTO()
        }
        println("  Retrieved ${page.designs.size} designs: ${page.designs.map(DesignDTO::id)}. Next token will be ${page.continuationToken}")
        retrievedDesigns.addAll(page.designs)
        nextContinuationToken = page.continuationToken
    }

    fun getAllRetrievedDesigns() = retrievedDesigns.toList()
}