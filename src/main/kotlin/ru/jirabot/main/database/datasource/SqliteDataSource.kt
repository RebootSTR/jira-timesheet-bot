package ru.jirabot.main.database.datasource

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.transactions.transaction
import ru.jirabot.di.DI
import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.main.database.tables.UserTable

object SqliteDataSource: MyDataSource() {

    private val settingsRepository: SettingsRepository = DI()

    private const val DB_URL = "jdbc:sqlite:database/base.sqlite3"
    private const val DB_DRIVER_NAME = "org.sqlite.JDBC"

    // todo надо бы сделать проверку на existance папки
    override fun init(db: Database) {
        println("Opened database: $${db.name}")
        transaction {
            SchemaUtils.create(
                UserTable
            )
        }
    }

    override fun connect() = Database.connect(
        url = DB_URL,
        driver = DB_DRIVER_NAME,
        user = settingsRepository.getSettingsValue(Settings.DB_USER),
        password = settingsRepository.getSettingsValue(Settings.DB_PASSWORD)
    )
}
