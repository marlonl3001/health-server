package br.com.mdr.routes

import br.com.mdr.domain.repository.AuthRepository
import br.com.mdr.model.AuthRequest
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(repository: AuthRepository) {

    post("/signup") {
        val request = call.receive<AuthRequest>()
        val success = repository.signup(request.email, request.password)

        if (success)
            call.respond(HttpStatusCode.Created)
        else
            call.respond(HttpStatusCode.Conflict, "Email já usado")
    }
}
