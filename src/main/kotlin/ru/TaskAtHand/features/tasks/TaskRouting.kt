package ru.TaskAtHand.features.tasks

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTaskRouting() {
    routing {
        post("/tasks/get") {
            val taskController = TaskController(call)
            taskController.getTasks()
        }
        post("/tasks/getForTaskUid") {
            val taskController = TaskController(call)
            taskController.getForTaskUid()
        }
        post("/tasks/updateCompleteDate") {
            val taskController = TaskController(call)
            taskController.updateCompleteDate()
        }

        post("/tasks/getListAllExecutors") {
            val taskController = TaskController(call)
            taskController.getListAllExecutors()
        }

        post("/tasks/getAllUsers") {
            val taskController = TaskController(call)
            taskController.getAllUsers()
        }

        /*post("/tasks/getAllUsers") {
            val taskController = TaskController(call)
            taskController.getAllUsers()
        }*/

        post("/tasks/getAllPrioritys") {
            val taskController = TaskController(call)
            taskController.getAllPrioritys()
        }

        post("/tasks/getAllRole") {
            val taskController = TaskController(call)
            taskController.getAllRole()
        }

        post("/tasks/getAllTasksStatus") {
            val taskController = TaskController(call)
            taskController.getAllTasksStatus()
        }

        post("/tasks/createTask") {
            val taskController = TaskController(call)
            taskController.createTask()
        }
    }
}