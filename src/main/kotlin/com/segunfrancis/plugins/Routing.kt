package com.segunfrancis.plugins

import com.segunfrancis.api.*
import com.segunfrancis.di.RepositoryDI.provideRepository
import com.segunfrancis.di.ServiceDI.provideJwtService
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

    val hashFunction = { s: String -> hash(s) }

    DatabaseFactory.init()

    val db = provideRepository()

    val jwtService = provideJwtService()

    routing {
        static("/static") {
            resources("images")
        }

        home(db)

        about(db)

        phrasesApi(db)

        phrases(db, hashFunction)

        signIn(db, hashFunction)

        signOut()

        //signup(db, hashFunction)

        login(db, jwtService)

        signup(db, jwtService, hashFunction)
    }
}
