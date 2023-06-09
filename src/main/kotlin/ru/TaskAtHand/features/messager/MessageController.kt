package ru.TaskAtHand.features.messager

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ru.TaskAtHand.database.message.Messages
import java.text.SimpleDateFormat
import java.util.*

class MessageController(private val call: ApplicationCall) {
    suspend fun sendMessage() {
        val receive = call.receive<SendMessageReceiveRemote>()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val timestamp: String = dateFormat.format(Date())

        transaction {
            Messages.insert { messageRow ->
                messageRow[Messages.sender_id] = receive.sender_id
                messageRow[Messages.receiver_id] = receive.receiver_id
                messageRow[Messages.message] = receive.message
                messageRow[Messages.timestamp] = timestamp
            }
        }

        call.respond(HttpStatusCode.Created, "Message sent successfully")
    }

    suspend fun getChatsForUsers() {
        val recive = call.receive<getChatsReceiveRemote>()

        val ChatList = transaction {
            val result = ArrayList<GetChatListResponce>()
            val chats = exec(
                """
                SELECT u.uid AS receiver_id, u.name, r.post, MAX(m."timestamp") AS last_timestamp, MAX(m.message) AS last_message
                FROM (
                  SELECT sender_id AS user_id
                  FROM messages
                  WHERE receiver_id = ${recive.uid}
                  
                  UNION
                  
                  SELECT receiver_id AS user_id
                  FROM messages
                  WHERE sender_id = ${recive.uid}
                ) AS users
                LEFT JOIN users u ON u.uid = users.user_id
                LEFT JOIN "role" r ON r.uid = u."role"
                LEFT JOIN messages m ON (m.sender_id = users.user_id OR m.receiver_id = users.user_id)
                GROUP BY u.uid, u.name, r.post;
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val receiverId = rs.getInt("receiver_id")
                    val name = rs.getString("name")
                    val post = rs.getString("post")
                    val timestamp = rs.getString("last_timestamp")
                    val message = rs.getString("last_message")
                    val chat = GetChatListResponce(receiverId, name, post, timestamp, message)
                    result.add(chat)
                }
            }
            result
        }
        call.respond(ChatList)
    }

    suspend fun getMessages() {
        val recive = call.receive<getMessegsReceiveRemote>()

        val MessageList = transaction {
            val result = ArrayList<GetMessagesResponce>()
            val messages = exec(
                """
            SELECT "timestamp", message, receiver_id, sender_id
            FROM messages
            WHERE (sender_id = ${recive.sender_id} AND receiver_id = ${recive.receiver_id}) OR (sender_id = ${recive.receiver_id} AND receiver_id = ${recive.sender_id})
            ORDER BY "timestamp"
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val timestamp = rs.getString("timestamp")
                    val message = rs.getString("message")
                    val receiver_id = rs.getInt("receiver_id")
                    val sender_id = rs.getInt("sender_id")
                    val chat = GetMessagesResponce(timestamp, message, sender_id, receiver_id)
                    result.add(chat)
                }
            }
            result
        }
        call.respond(MessageList)
    }

}