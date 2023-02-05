package ru.jirabot.main.repository

import ru.jirabot.domain.repository.SettingsRepository

class LocalSettingsRepository : SettingsRepository {

    // todo надо срочно переделать хранение инфы, чтобы не слить токен в гит

    override fun getJiraHost() = HOST
    override fun getTelegramToken() = TOKEN

    companion object {
        const val TOKEN = ""
        const val HOST = ""
    }

}