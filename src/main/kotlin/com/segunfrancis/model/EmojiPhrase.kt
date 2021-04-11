package com.segunfrancis.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

@Serializable
data class EmojiPhrase(val id: Int, val emoji: String, val phrase: String) : java.io.Serializable

object EmojiPhrases : IntIdTable() {
    var emoji: Column<String> = varchar("emoji", 255)
    var phrase: Column<String> = varchar("phrase", 255)
}
