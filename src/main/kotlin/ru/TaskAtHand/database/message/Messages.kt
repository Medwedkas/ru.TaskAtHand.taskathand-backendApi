package ru.TaskAtHand.database.message

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ru.TaskAtHand.database.users.UserModel

object Messages : Table("messages") {
    val id = Messages.integer("id").autoIncrement()
    val sender_id = Messages.integer("sender_id")
    val receiver_id = Messages.integer("receiver_id")
    val message = Messages.text("message")
    val timestamp = Messages.text("timestamp")


    fun insert(messageDTO: MessageDTO) {
        transaction {
            UserModel.insert {
                it[sender_id] = messageDTO.sender_id
                it[receiver_id] = messageDTO.receiver_id
                it[message] = messageDTO.message
                it[timestamp] = messageDTO.timestamp
            }
        }
    }
}

