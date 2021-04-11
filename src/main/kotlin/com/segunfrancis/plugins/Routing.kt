package com.segunfrancis.plugins

import com.segunfrancis.api.*
import com.segunfrancis.repository.*
import com.segunfrancis.webapp.*
import com.segunfrancis.webapp.home
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.content.*

fun Application.configureRouting() {

    val db = EmojiPhrasesRepository()
    routing {
        static("/static") {
            resources("images")
        }

        home()

        about()

        phrase(db)

        phrases(db)
    }
}
