package ru.jirabot.domain.repository

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.entities.User

interface UserRepository {

    fun saveUserAuth(user: User, auth: CharArray)

    fun saveUserState(user: User, state: BotState)

    fun getUserAuth(user: User): CharArray

    fun getUserState(user: User): BotState
}