package ru.jirabot.main.repository.sqlite

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.transactions.transaction
import ru.jirabot.di.DI
import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.main.repository.sqlite.tables.UserTable

class SqliteDataSource {

    private val settingsRepository: SettingsRepository = DI()

    private val db by lazy {
        Database.connect(
            url = DB_URL,
            driver = DB_DRIVER_NAME,
            user = settingsRepository.getSettingsValue(Settings.DB_USER),
            password = settingsRepository.getSettingsValue(Settings.DB_PASSWORD)
        )
    }

    init {
        println("Opened database: $${db.name}")
        transaction {
            SchemaUtils.drop(UserTable)
            SchemaUtils.create(
                UserTable
            )
        }
    }

    companion object {
        private const val DB_URL = "jdbc:sqlite:src/data/data.db"
        private const val DB_DRIVER_NAME = "org.sqlite.JDBC"
    }
}
