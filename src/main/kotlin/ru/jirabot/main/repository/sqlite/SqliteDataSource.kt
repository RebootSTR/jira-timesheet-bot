package ru.jirabot.main.repository.sqlite

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import ru.jirabot.main.repository.sqlite.tables.UserTable

class SqliteDataSource {

    private val db by lazy {
        Database.connect(DB_URL, DB_DRIVER_NAME)
    }

    init {
        println("Opened database: $${db.name}")
        SchemaUtils.create(
            UserTable
        )
    }

    companion object {
        private const val DB_URL = "jdbc:sqlite:/data/data.db"
        private const val DB_DRIVER_NAME = "org.sqlite.JDBC"
    }
}
