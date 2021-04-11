package com.segunfrancis.plugins

import com.segunfrancis.api.*
import com.segunfrancis.hash
import com.segunfrancis.repository.*
import com.segunfrancis.webapp.*
import com.segunfrancis.webapp.home
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.locations.KtorExperimentalLocationsAPI

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {

    val db = EmojiPhrasesRepository()

    val hashFunction = { s: String -> hash(s) }

    routing {
        static("/static") {
            resources("images")
        }

        home()

        about()

        phrase(db)

        phrases(db)

        signIn(db, hashFunction)

        signOut()

        signup(db, hashFunction)
    }
}
