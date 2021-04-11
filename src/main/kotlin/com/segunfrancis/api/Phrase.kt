package com.segunfrancis.api

import com.segunfrancis.*
import com.segunfrancis.model.*
import com.segunfrancis.repository.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val PHRASE_ENDPOINT: String = "$API_VERSION/phrase"

fun Route.phrase(db: Repository) {
    authenticate("auth") {
        post(PHRASE_ENDPOINT) {
            val request = call.receive<Request>()
            val phrase = db.add(emojiValue = request.emoji, phraseValue = request.phrase)
            call.respond(phrase)
        }
    }
}