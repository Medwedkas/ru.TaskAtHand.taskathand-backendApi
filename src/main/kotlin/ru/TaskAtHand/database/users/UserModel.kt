package ru.TaskAtHand.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserModel : Table("users") {
    private val uid = UserModel.integer("uid")
    private val number = UserModel.text("number")
    private val password = UserModel.text("password")
    private val role = UserModel.integer("role")
    private val name = UserModel.text("name")
    private val status = UserModel.text("status")

    fun insert(userDTO: UserDTO) {
        transaction {
            UserModel.insert {
                it[uid] = userDTO.uid
                it[number] = userDTO.number
                it[password] = userDTO.password
                it[role] = userDTO.role
                it[name] = userDTO.name
                it[status] = userDTO.status
            }
        }
    }

    fun selectUser(number: String): UserDTO {
        return transaction {
                val userModel = UserModel.select { UserModel.number.eq(number) }.single()
                UserDTO(
                    uid = userModel[UserModel.uid],
                    number = userModel[UserModel.number],
                    password = userModel[UserModel.password],
                    role = userModel[UserModel.role],
                    name = userModel[UserModel.name],
                    status = userModel[UserModel.status]
                )
        }
    }
}