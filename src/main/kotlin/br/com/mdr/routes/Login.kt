package br.com.mdr.routes

import br.com.mdr.domain.repository.AuthRepository
import br.com.mdr.model.AuthRequest
import br.com.mdr.model.AuthResponse
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.login(repository: AuthRepository) {

    post("/login") {
        try {
            val request = call.receive<AuthRequest>()
            val response: AuthResponse? = repository.login(request.email, request.password)

            if (response != null) {
                call.respond(response)
            } else {
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = "Invalid credentials"
                )
            }
        } catch(e: Exception) {
            call.respond(
                message = "Access denied.",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
}
