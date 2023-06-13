package ru.TaskAtHand.features.messager

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageReceiveRemote(
    val sender_id: Int,
    val receiver_id: Int,
    val message: String,
)

@Serializable
data class getChatsReceiveRemote(
    val uid: Int
)

@Serializable
data class getMessegsReceiveRemote(
    val sender_id: Int,
    val receiver_id: Int
)

@Serializable
data class GetChatListResponce(
    val receiver_id: Int,
    val name: String,
    val post:String,
    val timestamp:String,
    val message: String
)

@Serializable
data class GetMessagesResponce(
    val timestamp:String,
    val message: String,
    val sender_id: Int,
    val receiver_id: Int
)

