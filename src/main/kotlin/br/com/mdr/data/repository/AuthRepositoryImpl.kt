package br.com.mdr.data.repository

import at.favre.lib.crypto.bcrypt.BCrypt
import br.com.mdr.domain.model.User
import br.com.mdr.domain.repository.AuthRepository
import br.com.mdr.model.AuthResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.ConnectionString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.util.Date

class AuthRepositoryImpl(
    connectionString: String,
    private val jwtSecret: String
): AuthRepository {

    private val client = KMongo.createClient(ConnectionString(connectionString)).coroutine
    private val database = client.getDatabase("healthtracker")
    private val users = database.getCollection<User>()

    override suspend fun signup(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val user = users.findOne(User::email eq email)
        if (user != null) return@withContext false

        val hash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        users.insertOne(User(email = email, passwordHash = hash))

        return@withContext true
    }

    override suspend fun login(email: String, password: String): AuthResponse? = withContext(Dispatchers.IO) {
        val user = users.findOne(User::email eq email) ?: return@withContext null

        val result = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
        if (!result.verified) return@withContext null

        return@withContext AuthResponse(generateToken(user.id.toHexString(), user.email))
    }

    private fun generateToken(userId: String, email: String): String {
        val algorithm = Algorithm.HMAC256(jwtSecret)
        return JWT.create()
            .withSubject("Authentication")
            .withIssuer("healthtracker")
            .withClaim("userId", userId)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //expires in 24h
            .sign(algorithm)
    }
}