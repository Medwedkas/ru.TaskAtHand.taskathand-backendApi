package ru.TaskAtHand.features.tasks.models

import kotlinx.serialization.Serializable

//Структура ответа при getTask()
@Serializable
data class CreateTasksResponse(
    val uid: Int,
    val creator: String,
    val creatorRole: String,
    val executor: String,
    val executorRole: String,
    val description: String,
    val deadlines: String,
    val status: String,
    val header: String,
    val priority: String,
)

@Serializable
data class CreateTasksRequest(
    val uid: Int,
    val creator: String,
    val creatorRole: String,
    val executor: String,
    val executorRole: String,
    val description: String,
    val deadlines: String,
    val status: String,
    val header: String,
    val priority: String,
)

@Serializable
data class CreateTasksRequestAdmin(
    val creator: String,
    val creatorRole: String,
    val executor: String,
    val description: String,
    val deadlines: String,
    val status: String,
    val header: String,
    val priority: String,
)

@Serializable
data class CreateTaskResponse(
    val create: Boolean,
)

@Serializable
data class GetUser(
    val uid: Int
)

@Serializable
data class TaskCreator(
    val creator: Int
)

@Serializable
data class EditTasksRequest(
    val uid: Int,
    val creator: String,
    val creatorRole: String,
    val executor: String,
    val executorRole: String,
    val description: String,
    val deadlines: String,
    val status: String,
    val header: String,
    val priority: String,
)

//Задаем uid в теле запроса
@Serializable
data class TaskReceiveRemote(
    val uid: Int,
)

//Задаем uid в теле запроса, чтобы указать какую задачу обновить
@Serializable
data class UpdateTaskRemote(
    val uid: Int,
)

//
@Serializable
data class CreateBodyGetAllExecutors(
    val uid: Int,
)

@Serializable
data class AllUsersResponce(
    val uid: Int,
    val number: String,
    val password: String,
    val name: String,
    val status: String,
    val role: Int,
)


@Serializable
data class AllUsers(
    val uid: Int,
    val number: String,
    val password: String,
    val name: String,
    val status: String,
    val role: String,
)
@Serializable
data class AllPrioritysResponce(
    val uid: Int,
    val name: String,
)

@Serializable
data class AllRoleResponce(
    val uid: Int,
    val post: String,
)

@Serializable
data class AllTaskStatusResponce(
    val name: String,
)