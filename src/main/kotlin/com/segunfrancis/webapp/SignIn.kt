package com.segunfrancis.webapp

import com.segunfrancis.MIN_PASSWORD_LENGTH
import com.segunfrancis.MIN_USER_ID_LENGTH
import com.segunfrancis.model.EPSession
import com.segunfrancis.redirect
import com.segunfrancis.repository.Repository
import com.segunfrancis.userIdPattern
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set

private const val SIGN_IN: String = "/signin"

@KtorExperimentalLocationsAPI
@Location(SIGN_IN)
data class SignIn(val userId: String = "", val error: String = "")

@KtorExperimentalLocationsAPI
fun Route.signIn(db: Repository, hashFunction: (String) -> String) {

    post<SignIn> {
        fun userNameValid(userId: String) = userId.matches(userIdPattern)
        val signInParameters = call.receive<Parameters>()
        val userId = signInParameters["userId"] ?: return@post call.redirect(it)
        val password = signInParameters["password"] ?: return@post call.redirect(it)

        val signInError = SignIn(userId)

        val signin = when {
            userId.length < MIN_USER_ID_LENGTH -> null
            password.length < MIN_PASSWORD_LENGTH -> null
            !userNameValid(userId) -> null
            else -> db.user(userId, hashFunction(password))
        }

        if (signin == null) {
            call.redirect(signInError.copy(error = "Invalid username or password"))
        } else {
            call.sessions.set(EPSession(signin.userId))
            call.redirect(Phrases())
        }

    }

    get<SignIn> {
        val user = call.sessions.get<EPSession>()?.let { db.user(it.userId) }

        if (user != null) {
            call.redirect(Home())
        } else {
            call.respond(FreeMarkerContent("signin.ftl", mapOf("userId" to it.userId, "error" to it.error), ""))
        }
    }
}
