package com.segunfrancis.di

import com.segunfrancis.JwtService

object ServiceDI {

    private var jwtService: JwtService? = null

    fun provideJwtService() : JwtService {
        if (jwtService == null) {
            jwtService = JwtService()
        }
        return jwtService!!
    }
}