package ru.TaskAtHand.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(
    val number: String,
    val password: String
)

@Serializable
data class LoginResponseRemote(
    val auth: Boolean,
    val uid: Int?,
    val role:Int?,
    val name:String?,
)
