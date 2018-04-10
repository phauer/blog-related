package de.philipphauer.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun Application.module() {
    val designClient = MongoDesignClient()
    val mapper = ObjectMapper().registerKotlinModule()

    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/designs") {
            val designs = designClient.findDesigns()
            val json = mapper.writeValueAsString(designs)
            call.respondText(text = json, contentType = ContentType.Application.Json)
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8080
        , watchPaths = listOf("target")
        , module = Application::module
    ).start(wait = true)
}
