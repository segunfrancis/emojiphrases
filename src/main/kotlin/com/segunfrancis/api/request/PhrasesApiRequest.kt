package com.segunfrancis.api.request

import kotlinx.serialization.Serializable

data class PhrasesApiRequest(val emoji: String, val phrase: String)