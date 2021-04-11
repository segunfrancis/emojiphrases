package com.segunfrancis.webapp

import com.segunfrancis.redirect
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route

private const val SIGN_OUT: String = "/signout"

@KtorExperimentalLocationsAPI
@Location(SIGN_OUT)
class SignOut

@KtorExperimentalLocationsAPI
fun Route.signOut() {
    get<SignOut> {
        call.redirect(SignIn())
    }
}