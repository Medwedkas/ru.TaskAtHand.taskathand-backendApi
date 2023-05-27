package ru.TaskAtHand.features.login

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.TaskAtHand.database.users.UserModel

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = UserModel.selectUser(receive.number)
        if (userDTO.password == receive.password) {
            call.respond(LoginResponseRemote(auth = true, uid = userDTO.uid, role = userDTO.role, name = userDTO.name))
        } else {
            call.respond(LoginResponseRemote(auth = false, uid = null, role = null, name = null))
        }
    }
}