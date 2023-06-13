package ru.TaskAtHand.features.tasks

//import ru.TaskAtHand.database.tasks.mapToCreateTaskDTO


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.TaskAtHand.database.tasks.TaskDTO
import ru.TaskAtHand.features.tasks.models.*
import java.text.SimpleDateFormat
import java.util.*

class TaskController(private val call: ApplicationCall) {
    suspend fun getTasks() {
        val recive = call.receive<TaskReceiveRemote>()

        val taskDTOList = transaction {
            val result = ArrayList<TaskDTO>()

            val tasks = exec(
                """
                SELECT t.uid, u.name as creator, rlC.post as creatorRole,
                       uEx.name as executor, rlE.post as executorRole, 
                       t.header, t.description, pr.name as priority,
                	   t.deadlines, ts.name as status
                FROM tasks t
                JOIN users u ON u.uid = t.creator
                JOIN users uEx ON uEx.uid = t.executor
                JOIN prioritys pr ON pr.uid = t.priority
                JOIN role rlC ON rlC.uid = u.role
                JOIN role rlE ON rlE.uid = uEx.role
                JOIN task_status ts ON ts.uid = t.status 
                WHERE t.status = 1 AND t.executor = ${recive.uid}
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val taskDTO = TaskDTO(
                        uid = rs.getInt("uid"),
                        creator = rs.getString("creator"),
                        creatorRole = rs.getString("creatorRole"),
                        executor = rs.getString("executor"),
                        executorRole = rs.getString("executorRole"),
                        header = rs.getString("header"),
                        description = rs.getString("description"),
                        priority = rs.getString("priority"),
                        deadlines = rs.getString("deadlines"),
                        status = rs.getString("status")
                    )
                    result.add(taskDTO)
                }
            }
            result
        }
        call.respond(taskDTOList)
    }

    suspend fun getForTaskUid() {
        val recive = call.receive<TaskReceiveRemote>()
        val taskDTOList = transaction {
            val result = ArrayList<TaskDTO>()

            val tasks = exec(
                """
                SELECT t.uid, u.name as creator, rlC.post as creatorRole,
                       uEx.name as executor, rlE.post as executorRole, 
                       t.header, t.description, pr.name as priority,
                	   t.deadlines, ts.name as status
                FROM tasks t
                JOIN users u ON u.uid = t.creator
                JOIN users uEx ON uEx.uid = t.executor
                JOIN prioritys pr ON pr.uid = t.priority
                JOIN role rlC ON rlC.uid = u.role
                JOIN role rlE ON rlE.uid = uEx.role
                JOIN task_status ts ON ts.uid = t.status 
                WHERE t.uid = ${recive.uid}
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val taskDTO = TaskDTO(
                        uid = rs.getInt("uid"),
                        creator = rs.getString("creator"),
                        creatorRole = rs.getString("creatorRole"),
                        executor = rs.getString("executor"),
                        executorRole = rs.getString("executorRole"),
                        header = rs.getString("header"),
                        description = rs.getString("description"),
                        priority = rs.getString("priority"),
                        deadlines = rs.getString("deadlines"),
                        status = rs.getString("status")
                    )
                    result.add(taskDTO)
                }
            }
            result
        }
        call.respond(taskDTOList)
    }

    suspend fun updateCompleteDate() {
        val recive = call.receive<UpdateTaskRemote>()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        val completeDate: String = dateFormat.format(Date())

        val requestStatus = transaction {
            try {
                exec(
                    """
                UPDATE tasks
                SET "completeDate" = '${completeDate}',
                status = 2
                WHERE uid = ${recive.uid}
                """
                )
                true // Обновление выполнено успешно
            } catch (e: Exception) {
                false // Произошла ошибка при обновлении
            }
        }

        call.respond(HttpStatusCode.OK, mapOf("request" to requestStatus))
    }

    suspend fun getListAllExecutors() {
        val recive = call.receive<CreateBodyGetAllExecutors>()
        val taskDTOList = transaction {
            val result = ArrayList<TaskDTO>()

            val tasks = exec(
                """
                    SELECT t.uid, u.name as creator, rlC.post as creatorRole,
                           uEx.name as executor, rlE.post as executorRole, 
                           t.header, t.description, pr.name as priority,
                    	   t.deadlines, ts.name as status
                    FROM tasks t
                    JOIN users u ON u.uid = t.creator
                    JOIN users uEx ON uEx.uid = t.executor
                    JOIN prioritys pr ON pr.uid = t.priority
                    JOIN role rlC ON rlC.uid = u.role
                    JOIN role rlE ON rlE.uid = uEx.role
                    JOIN task_status ts ON ts.uid = t.status 
                    WHERE creator = ${recive.uid}
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val taskDTO = TaskDTO(
                        uid = rs.getInt("uid"),
                        creator = rs.getString("creator"),
                        creatorRole = rs.getString("creatorRole"),
                        executor = rs.getString("executor"),
                        executorRole = rs.getString("executorRole"),
                        header = rs.getString("header"),
                        description = rs.getString("description"),
                        priority = rs.getString("priority"),
                        deadlines = rs.getString("deadlines"),
                        status = rs.getString("status")
                    )
                    result.add(taskDTO)
                }
            }
            result
        }
        call.respond(taskDTOList)
    }

    suspend fun getAllUsers() {
        val taskDTOList = transaction {
            val result = ArrayList<AllUsers>()

            val tasks = exec(
                """
                SELECT u.uid, u.number, u."password", u."name", u.status, r.post as role
                FROM users u
                JOIN "role" r ON r.uid = u."role"
                ORDER BY u.role;
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val userDTO = AllUsers(

                        uid = rs.getInt("uid"),
                        number = rs.getString("number"),
                        password = rs.getString("password"),
                        name = rs.getString("name"),
                        status = rs.getString("status"),
                        role = rs.getString("role"),
                    )
                    result.add(userDTO)
                }
            }
            result
        }
        call.respond(taskDTOList)
    }

    suspend fun getAllPrioritys() {
        val prioritysDTOList = transaction {
            val result = ArrayList<AllPrioritysResponce>()

            val tasks = exec(
                """
                select * from prioritys 
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val prioritysDTO = AllPrioritysResponce(

                        uid = rs.getInt("uid"),
                        name = rs.getString("name"),
                    )
                    result.add(prioritysDTO)
                }
            }
            result
        }
        call.respond(prioritysDTOList)
    }

    suspend fun getAllRole() {
        val roleDTOList = transaction {
            val result = ArrayList<AllRoleResponce>()

            val tasks = exec(
                """
                select * from role 
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val roleDTO = AllRoleResponce(

                        uid = rs.getInt("uid"),
                        post = rs.getString("post"),
                    )
                    result.add(roleDTO)
                }
            }
            result
        }
        call.respond(roleDTOList)
    }

    suspend fun getAllTasksStatus() {
        val TasksStatusDTOList = transaction {
            val result = ArrayList<AllTaskStatusResponce>()

            val tasks = exec(
                """
                select * from task_status 
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val TasksStatusDTO = AllTaskStatusResponce(
                        name = rs.getString("name"),
                    )
                    result.add(TasksStatusDTO)
                }
            }
            result
        }
        call.respond(TasksStatusDTOList)
    }

    suspend fun createTask() {
        val recive = call.receive<CreateTasksRequestAdmin>()
        val taskDTOList = transaction {
            val tasks = exec(
                """
                    WITH vars AS (
                      SELECT
                        ${recive.creator} AS creator,
                        ${recive.creatorRole} AS creator_role,
                        '${recive.executor}' AS executor_name,
                        NULL AS executor_role,
                        '${recive.description}' AS description,
                        '${recive.deadlines}'::timestamp AS deadlines,
                        '${recive.status}' AS status,
                        '${recive.priority}' AS priority,
                        '${recive.header}' AS header
                    )
                    INSERT INTO tasks (creator, creator_role, executor, executor_role, description, deadlines, status, priority, header)
                    SELECT
                        uCreator.uid,
                        rlCreator.uid,
                        uExecutor.uid,
                        rlExecutor.uid,
                        description,
                        deadlines,
                        ts.uid,
                        pr.uid,
                        header
                    FROM
                        vars
                        JOIN users uCreator ON uCreator.uid = vars.creator
                        JOIN role rlCreator ON rlCreator.uid = uCreator.role
                        JOIN users uExecutor ON uExecutor.name = vars.executor_name
                        JOIN role rlExecutor ON rlExecutor.uid = uExecutor.role
                        CROSS JOIN task_status ts
                        JOIN prioritys pr ON pr.name = vars.priority
                    ORDER BY ts.uid
                    LIMIT 1
            """.trimIndent()
            )
        }
        call.respond(taskDTOList)
    }

    suspend fun getTaskCreator() {
        val recive = call.receive<GetUser>()
        val taskDTOList = transaction {
            val result = ArrayList<TaskCreator>()

            val tasks = exec(
                """
                select * from tasks where uid = ${recive.uid}
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val userDTO = TaskCreator(
                        creator = rs.getInt("creator"),
                    )
                    result.add(userDTO)
                }
            }
            result
        }
        call.respond(taskDTOList)

    }

    suspend fun getUser() {
        val recive = call.receive<GetUser>()
        val taskDTOList = transaction {
            val result = ArrayList<AllUsersResponce>()

            val tasks = exec(
                """
                select * from users where uid = ${recive.uid}
            """.trimIndent()
            ) { rs ->
                while (rs.next()) {
                    val userDTO = AllUsersResponce(

                        uid = rs.getInt("uid"),
                        number = rs.getString("number"),
                        password = rs.getString("password"),
                        name = rs.getString("name"),
                        status = rs.getString("status"),
                        role = rs.getInt("role"),
                    )
                    result.add(userDTO)
                }
            }
            result
        }
        call.respond(taskDTOList)
    }
}