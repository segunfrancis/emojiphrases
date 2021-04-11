package com.segunfrancis.api

import com.segunfrancis.API_VERSION
import com.segunfrancis.model.Request
import com.segunfrancis.repository.Repository
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val PHRASE_ENDPOINT: String = "$API_VERSION/phrase"

@KtorExperimentalLocationsAPI
@Location(PHRASE_ENDPOINT)
class Phrase

@KtorExperimentalLocationsAPI
fun Route.phrase(db: Repository) {
    post<Phrase> {
        val request = call.receive<Request>()
        val phrase = db.add("", emojiValue = request.emoji, phraseValue = request.phrase)
        call.respond(phrase)
    }
}
