package ru.jirabot.main.repository

import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import java.io.FileInputStream
import java.util.*

class LocalSettingsRepository : SettingsRepository {

    private val properties: Properties = Properties().apply {
        load(FileInputStream(PROPERTIES_FILE_PATH))
    }

    private fun getSettingValue(type: Settings): String {
        return when (type) {
            is Settings.TG_TOKEN -> properties.getProperty(BOT_TOKEN_PROPS_KEY)
            is Settings.JIRA_HOST -> properties.getProperty(JIRA_HOST_PROPS_KEY)
        }
    }

    override fun getJiraHost() = getSettingValue(Settings.JIRA_HOST)
    override fun getTelegramToken() = getSettingValue(Settings.TG_TOKEN)

    companion object {
        private const val PROPERTIES_FILE_PATH = "local.properties"

        private const val BOT_TOKEN_PROPS_KEY = "bot.token"
        private const val JIRA_HOST_PROPS_KEY = "jira.host"
    }
}


