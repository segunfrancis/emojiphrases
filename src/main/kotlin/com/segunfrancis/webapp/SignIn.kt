package com.segunfrancis.webapp

import com.segunfrancis.repository.Repository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

private const val SIGN_IN: String = "/signin"

@KtorExperimentalLocationsAPI
@Location(SIGN_IN)
data class SignIn(val userId: String = "", val error: String = "")

@KtorExperimentalLocationsAPI
fun Route.signIn(db: Repository, hashFunction: (String) -> String) {
    get<SignIn> {
        call.respond(FreeMarkerContent("signin.ftl", null))
    }
}
