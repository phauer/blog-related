package de.philipphauer.blog

import org.eclipse.jetty.server.NCSARequestLog
import org.eclipse.jetty.server.Server
import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main(args: Array<String>) {
    val resource = DesignResource()

    val routingHandler = routes(
            "/designs" bind Method.GET to resource::getDesigns
    )
    val jetty = Server(8000).apply {
        requestLog = NCSARequestLog()
    }
    val server = routingHandler.asServer(Jetty(jetty)).start()
    server.block()
}

