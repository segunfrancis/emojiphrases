package com.segunfrancis.api

import com.segunfrancis.API_VERSION
import com.segunfrancis.repository.Repository
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.routing.Route

const val PHRASE_ENDPOINT: String = "$API_VERSION/phrase"

@KtorExperimentalLocationsAPI
@Location(PHRASE_ENDPOINT)
class PhrasesApi

@KtorExperimentalLocationsAPI
fun Route.phrasesApi(db: Repository) {
    post<PhrasesApi> {


    }
}
