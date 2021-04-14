package com.segunfrancis.api

import com.segunfrancis.API_VERSION
import com.segunfrancis.api.request.PhrasesApiRequest
import com.segunfrancis.apiUser
import com.segunfrancis.repository.Repository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route

const val PHRASE_ENDPOINT: String = "$API_VERSION/phrase"

@KtorExperimentalLocationsAPI
@Location(PHRASE_ENDPOINT)
class PhrasesApi

@KtorExperimentalLocationsAPI
fun Route.phrasesApi(db: Repository) {
    //authenticate("jwt") {
        get<PhrasesApi> {
            call.respond(db.phrases())
        }

        post<PhrasesApi> {
            //val user = call.apiUser!!

            try {
                val request = call.receive<PhrasesApiRequest>()
                val phrase = db.add("userId", request.emoji, request.phrase)
                if (phrase != null) {
                    call.respond(phrase)
                } else {
                    call.respondText("invalid data received", status = HttpStatusCode.InternalServerError)
                }

            } catch (e: Throwable) {
                call.respondText("invalid data received: ${e.localizedMessage}", status = HttpStatusCode.BadRequest)
            }
        }
    //}
}
