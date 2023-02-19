package ru.jirabot.data.repository.sqlite

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.jirabot.data.database.tables.UserDao
import ru.jirabot.data.database.tables.UserTable
import ru.jirabot.data.repository.cache.Cache
import ru.jirabot.data.repository.cache.Cached
import ru.jirabot.data.utils.Serializer.deserializeBotState
import ru.jirabot.data.utils.Serializer.serialize
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.ui.states.logic2.InitState

class SqliteUserRepository : UserRepository {

    override fun saveUserAuth(user: User, auth: CharArray) = Cache.invalidateAfter(
        after = listOf(UserRepository::getUserAuth, user)
    ) {
        transaction {
            UserTable
                .update({ UserTable.botId eq user.userId })
                {
                    it[UserTable.auth] = String(auth)
                }

            commit()
        }
    }

    override fun saveUserState(user: User, state: BotState) {
        transaction {
            UserTable.update({ UserTable.botId eq user.userId }) {
                it[lastState] = state.serialize()
            }

            commit()
        }
    }

    @Cached
    override fun getUserAuth(user: User): CharArray = Cache.cached(
        listOf(UserRepository::getUserAuth, user)
    ) {
        return@cached transaction {
            UserDao.find {
                UserTable.botId eq user.userId
            }.limit(1).map { dao ->
                dao.auth?.toCharArray() ?: throw IllegalArgumentException("No 'auth' saved for user ${user.userId}")
            }.first()
        }
    }

    override fun getUserState(user: User): BotState = transaction {
        val iterable = UserDao.find {
            UserTable.botId eq user.userId
        }

        if (iterable.empty() || iterable.first().lastState.isNullOrBlank()) {
            saveNewUser(user)
            InitState()
        } else {
            // на случай если после перезапуска бота, стейта уже не будет либо он станет не валидным
            try {
                iterable.first().lastState!!.deserializeBotState()
            } catch (ex: Exception) {
                InitState()
            }
        }
    }

    override fun isUserExist(user: User): Boolean =
        transaction {
            val iterable = UserDao.find {
                UserTable.botId eq user.userId
            }
            if (iterable.empty()) {
                false
            } else {
                iterable.limit(1).toList()[0].auth != null
            }
        }

    private fun saveNewUser(user: User) {
        transaction {
            UserDao.new {
                botId = user.userId
            }
            commit()
        }
    }
}
