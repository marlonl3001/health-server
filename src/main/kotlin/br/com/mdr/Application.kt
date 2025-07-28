package br.com.mdr

import br.com.mdr.plugin.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    val jwtSecret = System.getenv("JWT_SECRET") ?: "super-secret"
    configureKoin(jwtSecret)
    configureSecurity(jwtSecret)

    configureSerialization()
    configureMonitoring()
    configureRouting()
    configureDefaultHeader()
    configureStatusPages()
}