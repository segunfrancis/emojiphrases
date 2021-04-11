package com.segunfrancis

import com.segunfrancis.model.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.segunfrancis.plugins.*
import com.segunfrancis.repository.DatabaseFactory
import freemarker.cache.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.serialization.*
import kotlinx.serialization.json.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(DefaultHeaders)

        install(StatusPages) {
            exception<Throwable> { e ->
                call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
            }
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }

        install(Authentication) {
            basic(name = "auth") {
                realm = "ktor service"
                validate { credentials ->
                    if (credentials.password == "${credentials.name}123") User(credentials.name) else null
                }
            }
        }

        install(Locations)

        DatabaseFactory.init()

        configureRouting()

    }.start(wait = true)
}

const val API_VERSION: String = "/api/v1"

@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}
