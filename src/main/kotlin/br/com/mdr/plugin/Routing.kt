package br.com.mdr.plugin

import br.com.mdr.domain.repository.AuthRepository
import br.com.mdr.routes.login
import br.com.mdr.routes.me
import br.com.mdr.routes.root
import br.com.mdr.routes.signUp
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authRepository: AuthRepository by inject()

    routing {
        root()
        signUp(authRepository)
        login(authRepository)
        me()
    }
}