package ru.jirabot.domain.repository

interface SettingsRepository {

    fun getSettingsValue(settings: Settings): String
}

enum class Settings {
    TG_TOKEN,
    JIRA_HOST,
    JIRA_TIMEZONE,
    DB_USER,
    DB_PASSWORD
    ;
}
