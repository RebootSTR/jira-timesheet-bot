package ru.jirabot.domain.repository

interface SettingsRepository {

    fun getJiraHost(): String

    fun getTelegramToken(): String
}

sealed class Settings {
    object TG_TOKEN : Settings()
    object JIRA_HOST : Settings()
}
