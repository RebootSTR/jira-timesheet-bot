package ru.jirabot.domain.repository

import ru.jirabot.domain.bot.BotState

interface UserRepository<User> {

    fun saveUserAuth(user: User, auth: CharArray)

    fun saveUserState(user: User, state: BotState<User>)

    fun getUserAuth(user: User): CharArray

    fun getUserState(user: User): BotState<User>
}