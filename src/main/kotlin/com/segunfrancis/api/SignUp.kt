package com.segunfrancis.api

import com.segunfrancis.API_VERSION
import com.segunfrancis.JwtService
import com.segunfrancis.api.request.SignupApiRequest
import com.segunfrancis.model.User
import com.segunfrancis.redirect
import com.segunfrancis.repository.Repository
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import java.util.*

private const val SIGNUP_ENDPOINT: String = "$API_VERSION/signup"

@KtorExperimentalLocationsAPI
@Location(SIGNUP_ENDPOINT)
data class SignUp(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val error: String = ""
)

@KtorExperimentalLocationsAPI
fun Route.signup(db: Repository, jwtService: JwtService, hashFunction: (String) -> String) {

    val MIN_USER_ID_LENGTH: Int = 4
    val MIN_PASSWORD_LENGTH: Int = 6
    val userIdPattern = "[a-zA-Z0-9_\\.]+".toRegex()
    fun userNameValid(userId: String) = userId.matches(userIdPattern)

    post<SignUp> {
        val params = call.receive<SignupApiRequest>()
        val uuid = UUID.randomUUID().toString()
        val userId = uuid.dropLast(28)
        val password = params.password ?: return@post call.redirect(it)
        val email = params.email ?: return@post call.redirect(it)
        val displayName = params.displayName ?: return@post call.redirect(it)

        when {
            password.length < MIN_PASSWORD_LENGTH -> call.respondText("Password should be at least $MIN_PASSWORD_LENGTH characters long")
            userId.length < MIN_USER_ID_LENGTH -> call.respondText("Username should be at least $MIN_USER_ID_LENGTH characters long")
            !userNameValid(userId) -> call.respondText("Username should be consists of digits, letters, dots or underscores")
            db.user(userId) != null -> call.respondText("User with the following username is already registered")
            else -> {
                val hash = hashFunction(password)
                val newUser = User(userId, email, displayName, hash)

                try {
                    db.createUser(newUser)
                } catch (e: Throwable) {
                    when {
                        db.user(userId) != null -> call.respondText("User with the following username is already registered")
                        db.userByEmail(email) != null -> call.respondText("User with the following email $email is already registered")
                        else -> {
                            application.log.error("Failed to register user", e)
                            call.respondText("Failed to register")
                        }
                    }
                }

                val token = jwtService.generateToken(newUser)
                call.respondText(token)
            }
        }
    }
}
