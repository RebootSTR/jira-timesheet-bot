package ru.jirabot.main.repository

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.main.states.InitState
import ru.jirabot.telegram.TelegramUser

class LocalUserRepository : UserRepository<TelegramUser> {

    // todo реализация сохранения в базу в другом репозитории (желательно с кэшированием но пока out of scope)
    private val stateStorage = mutableMapOf<TelegramUser, BotState<TelegramUser>>()
    private val authStorage = mutableMapOf<TelegramUser, CharArray>()

    override fun saveUserAuth(user: TelegramUser, auth: CharArray) {
        authStorage[user] = auth
    }

    override fun saveUserState(user: TelegramUser, state: BotState<TelegramUser>) {
        stateStorage[user] = state
    }

    override fun getUserAuth(user: TelegramUser): CharArray {
        return authStorage[user]!!
    }

    override fun getUserState(user: TelegramUser): BotState<TelegramUser> {
        return stateStorage[user] ?: kotlin.run {
            InitState().apply {
                stateStorage[user] = this
            }
        }
    }

}