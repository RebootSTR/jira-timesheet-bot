package ru.jirabot.data.database.datasource

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.jirabot.data.database.tables.TemplateTable
import ru.jirabot.data.database.tables.UserTable
import ru.jirabot.data.utils.FilesUtils
import ru.jirabot.di.DI
import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository

object SqliteDataSource : MyDataSource() {

    private val settingsRepository: SettingsRepository = DI()
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    private const val DB_PATH = "database/base.sqlite3"
    private const val DB_URL = "jdbc:sqlite:$DB_PATH"
    private const val DB_DRIVER_NAME = "org.sqlite.JDBC"

    override fun init(db: Database) {
        FilesUtils.touch(DB_PATH)
        logger.info("Opened database: $${db.name}")
        transaction {
            SchemaUtils.create(
                UserTable,
                TemplateTable
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
