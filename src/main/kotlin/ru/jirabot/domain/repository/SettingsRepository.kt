package ru.jirabot.domain.repository

interface SettingsRepository {

    fun getSettingsValue(settings: Settings): String
}

sealed class Settings {
    object TG_TOKEN : Settings()
    object JIRA_HOST : Settings()
    object DB_USER : Settings()
    object DB_PASSWORD : Settings()
}
