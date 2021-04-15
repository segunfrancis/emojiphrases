package com.segunfrancis.api.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupApiRequest(val email: String?, val password: String?, val displayName: String?)
