package com.segunfrancis.api

import com.segunfrancis.API_VERSION
import com.segunfrancis.JwtService
import com.segunfrancis.hash
import com.segunfrancis.redirect
import com.segunfrancis.repository.Repository
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route

const val LOGIN_ENDPOINT = "$API_VERSION/login"

@KtorExperimentalLocationsAPI
@Location(LOGIN_ENDPOINT)
class Login

@KtorExperimentalLocationsAPI
fun Route.login(db: Repository, jwtService: JwtService) {

    post<Login> {
        val params = call.receive<Parameters>()
        val userId = params["userId"] ?: return@post call.redirect(it)
        val password = params["password"] ?: return@post call.redirect(it)

        val user = db.user(userId, hash(password))
        if (user != null) {
            val token = jwtService.generateToken(user)
            call.respondText(token)
        } else {
            call.respondText("Invalid user")
        }
    }
}