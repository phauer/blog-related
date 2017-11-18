package de.philipphauer.blog

import de.philipphauer.blog.util.FunctionsMySQL
import org.eclipse.jetty.server.NCSARequestLog
import org.eclipse.jetty.server.Server
import org.h2.jdbcx.JdbcDataSource
import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ScriptUtils

fun main(args: Array<String>) {
    val resource = bootstrapDesignResource()

    val routingHandler = routes(
            "/designs" bind Method.GET to resource::getDesigns
    )
    val jetty = Server(8000).apply {
        requestLog = NCSARequestLog()
    }
    val server = routingHandler.asServer(Jetty(jetty)).start()
    server.block()
}

private fun bootstrapDesignResource(): DesignResource {
    val dataSource = JdbcDataSource().apply {
        user = "sa"
        password = ""
        setURL("jdbc:h2:mem:access;MODE=MySQL;DB_CLOSE_DELAY=-1")
    }
    FunctionsMySQL.register(dataSource.connection)
    ScriptUtils.executeSqlScript(dataSource.connection,  ClassPathResource("create-designs-table.sql"))
    val dao = DesignDAO(dataSource)
    return DesignResource(dao)
}

