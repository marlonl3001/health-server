package br.com.mdr.routes

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.me() {
    authenticate("auth-jwt") {
        get("/me") {
            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
            val userId = principal?.payload?.getClaim("userId")?.asString()

            call.respond(mapOf("userId" to userId, "email" to email))
        }
    }
}