package ru.jirabot.main.repository

import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import java.io.FileInputStream
import java.util.*

class LocalSettingsRepository : SettingsRepository {

    private val properties: Properties = Properties().apply {
        load(FileInputStream(PROPERTIES_FILE_PATH))
    }

    override fun getSettingsValue(settings: Settings): String {
        return when (settings) {
            is Settings.TG_TOKEN -> properties.getProperty(BOT_TOKEN_PROPS_KEY)
            is Settings.JIRA_HOST -> properties.getProperty(JIRA_HOST_PROPS_KEY)
            is Settings.DB_USER -> properties.getProperty(DB_USER_PROPS_KEY)
            is Settings.DB_PASSWORD -> properties.getProperty(DB_PASSWORD_PROPS_KEY)
        }
    }

    companion object {
        private const val PROPERTIES_FILE_PATH = "local.properties"

        private const val BOT_TOKEN_PROPS_KEY = "bot.token"
        private const val JIRA_HOST_PROPS_KEY = "jira.host"
        private const val DB_USER_PROPS_KEY = "sqlite.user"
        private const val DB_PASSWORD_PROPS_KEY = "sqlite.password"
    }
}


