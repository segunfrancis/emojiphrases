package com.segunfrancis.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

@Serializable
data class EmojiPhrase(
    val id: Int, val userId: String, val emoji: String, val phrase: String
) : java.io.Serializable

object EmojiPhrases : IntIdTable() {
    var user: Column<String> = varchar("user_id", 20).index()
    var emoji: Column<String> = varchar("emoji", 255)
    var phrase: Column<String> = varchar("phrase", 255)
}
