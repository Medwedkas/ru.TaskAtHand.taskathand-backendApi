package ru.TaskAtHand

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.TaskAtHand.features.login.configureLoginRouting
import ru.TaskAtHand.features.messager.configureMessageRouting
import ru.TaskAtHand.features.tasks.configureTaskRouting
import ru.TaskAtHand.plugins.configureMonitoring
import ru.TaskAtHand.plugins.configureRouting
import ru.TaskAtHand.plugins.configureSerialization

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/TaskAtHand",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "553al1703"
    )
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureLoginRouting()
    configureTaskRouting()
    configureRouting()
    configureMessageRouting()
}
