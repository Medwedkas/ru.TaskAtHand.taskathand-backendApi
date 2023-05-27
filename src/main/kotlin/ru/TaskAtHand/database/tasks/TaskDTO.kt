package ru.TaskAtHand.database.tasks

import kotlinx.serialization.Serializable
import ru.TaskAtHand.features.tasks.models.CreateTasksResponse

@Serializable
class TaskDTO(
    val uid: Int,
    val creator: String,
    val creatorRole: String,
    val executor: String,
    val executorRole: String,
    val description: String,
    val deadlines: String,
    val status: String,
    val header: String,
    val priority: String
)


fun TaskDTO.mapToGetTasks(): CreateTasksResponse =
    CreateTasksResponse(
        uid = uid,
        creator = creator,
        creatorRole = creatorRole,
        executor = executor,
        executorRole = executorRole,
        description = description,
        deadlines = deadlines,
        status = status,
        header = header,
        priority = priority
    )

/*
fun CreateTasksRequest.mapToCreateTaskDTO(): TaskDTO =
    TaskDTO(
        uid = uid,
        creator = creator,
        creatorRole = creatorRole,
        executor = executor,
        executorRole = executorRole,
        description = description,
        deadlines = deadlines,
        status = status
    )*/
