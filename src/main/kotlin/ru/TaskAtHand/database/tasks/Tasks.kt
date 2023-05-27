package ru.TaskAtHand.database.tasks

import org.jetbrains.exposed.sql.Table


object Tasks : Table("tasks") {
    val uid = Tasks.integer("uid").autoIncrement()
    val creator = Tasks.text("creator")
    val creatorRole = Tasks.text("creator_role")
    val executor = Tasks.text("executor")
    val executorRole = Tasks.text("executor_role")
    val description = Tasks.varchar("description", 255)
    val deadlines = Tasks.text("deadlines")
    val status = Tasks.text("status")
    val header = Tasks.text("header")
    val priority = Tasks.text("priority")

}
