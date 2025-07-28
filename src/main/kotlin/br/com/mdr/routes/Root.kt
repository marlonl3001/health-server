package br.com.mdr.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.root() {
    get("/") {
        call.respond(
            message = "Wellcome to Health Track API.",
            status = HttpStatusCode.OK
        )
    }
}