package br.com.mdr.domain.repository

import br.com.mdr.model.AuthResponse

interface AuthRepository {
    suspend fun signup(email: String, password: String): Boolean
    suspend fun login(email: String, password: String): AuthResponse?
}