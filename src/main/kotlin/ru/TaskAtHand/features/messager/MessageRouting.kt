package ru.TaskAtHand.features.messager

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureMessageRouting() {
    routing {
        post("/message/send") {
            val messageController = MessageController(call)
            messageController.sendMessage()
        }
        post("/message/getChats") {
            val messageController = MessageController(call)
            messageController.getChatsForUsers()
        }
        post("/message/getMessages") {
            val messageController = MessageController(call)
            messageController.getMessages()
        }
    }
}