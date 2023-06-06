package ru.TaskAtHand.database.message

import kotlinx.serialization.Serializable

@Serializable
class MessageDTO(
    val id: Int,
    val sender_id: Int,
    val receiver_id: Int,
    val message : String,
    val timestamp: String,
)