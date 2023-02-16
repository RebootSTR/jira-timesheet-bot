package ru.jirabot.data.database

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.jirabot.data.database.tables.UserDao
import ru.jirabot.data.database.tables.UserTable
import ru.jirabot.data.utils.Serializer.deserializeBotState
import ru.jirabot.data.utils.Serializer.serialize
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.ui.common.states.InitState

class SqliteUserRepository : UserRepository {

    override fun saveUserAuth(user: User, auth: CharArray) {
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

    override fun getUserAuth(user: User): CharArray {
        return transaction {
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
            iterable.first().lastState!!.deserializeBotState()
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
