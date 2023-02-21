package ru.jirabot.data.repository

import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import java.io.FileInputStream
import java.util.*

class LocalSettingsRepository : SettingsRepository {

    private val properties: Properties = Properties().apply {
        FileInputStream(PROPERTIES_FILE_PATH).use {
            load(it)
        }
    }

    override fun getSettingsValue(settings: Settings): String {
        return when (settings) {
            Settings.TG_TOKEN -> properties.getProperty(BOT_TOKEN_PROPS_KEY)
            Settings.JIRA_HOST -> properties.getProperty(JIRA_HOST_PROPS_KEY)
            Settings.DB_USER -> properties.getProperty(DB_USER_PROPS_KEY)
            Settings.DB_PASSWORD -> properties.getProperty(DB_PASSWORD_PROPS_KEY)
            Settings.JIRA_TIMEZONE -> properties.getProperty(JIRA_TIMEZONE)
        }
    }

    companion object {
        private const val PROPERTIES_FILE_PATH = "local.properties"

        private const val BOT_TOKEN_PROPS_KEY = "bot.token"
        private const val JIRA_HOST_PROPS_KEY = "jira.host"
        private const val JIRA_TIMEZONE = "jira.timezone"
        private const val DB_USER_PROPS_KEY = "sqlite.user"
        private const val DB_PASSWORD_PROPS_KEY = "sqlite.password"
    }
}


