package br.com.mdr.di

import br.com.mdr.domain.repository.AuthRepository
import br.com.mdr.data.repository.AuthRepositoryImpl
import org.koin.dsl.module

fun serverModule(jwtSecret: String) = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            connectionString = "mongodb://localhost:27017",
            jwtSecret = jwtSecret
        )
    }
}