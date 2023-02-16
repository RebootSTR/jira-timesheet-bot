package ru.jirabot.data.repository

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.ui.common.states.InitState
import ru.jirabot.domain.entities.User

class LocalUserRepository : UserRepository {

    // todo реализация сохранения в базу в другом репозитории (желательно с кэшированием но пока out of scope)
    private val stateStorage = mutableMapOf<User, BotState>()
    private val authStorage = mutableMapOf<User, CharArray>()

    override fun saveUserAuth(user: User, auth: CharArray) {
        authStorage[user] = auth
    }

    override fun saveUserState(user: User, state: BotState) {
        stateStorage[user] = state
    }

    override fun getUserAuth(user: User): CharArray {
        return authStorage[user]!!
    }

    override fun getUserState(user: User): BotState {
        return stateStorage[user] ?: kotlin.run {
            InitState().apply {
                stateStorage[user] = this
            }
        }
    }

}
