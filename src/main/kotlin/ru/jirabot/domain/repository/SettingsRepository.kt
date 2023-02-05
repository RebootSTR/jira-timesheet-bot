package ru.jirabot.domain.repository

interface SettingsRepository {

    fun getJiraHost(): String

    fun getTelegramToken(): String
}