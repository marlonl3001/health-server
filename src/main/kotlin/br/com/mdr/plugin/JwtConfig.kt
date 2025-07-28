package br.com.mdr.plugin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.http.*

fun Application.configureSecurity(secret: String) {
    authentication {
        jwt("auth-jwt") {
            realm = "healthtracker"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withIssuer("healthtracker")
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("userId").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token inválido ou ausente")
            }
        }
    }
}