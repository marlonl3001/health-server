package br.com.mdr.plugin

import br.com.mdr.di.serverModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.koin

fun Application.configureKoin(jwtSecret: String) {
    koin {
        modules(serverModule(jwtSecret))
    }
}