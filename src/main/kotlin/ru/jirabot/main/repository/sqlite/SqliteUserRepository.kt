package ru.jirabot.main.repository.sqlite

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.main.Serializer.deserializeBotState
import ru.jirabot.main.Serializer.serialize
import ru.jirabot.main.repository.sqlite.tables.UserDao
import ru.jirabot.main.repository.sqlite.tables.UserTable
import ru.jirabot.main.states.InitState

class SqliteUserRepository : UserRepository<User> {

    override fun saveUserAuth(user: User, auth: CharArray) {
        transaction {
            UserTable
                .update({ UserTable.botId eq user.botId })
                {
                    it[UserTable.auth] = String(auth)
                }

            commit()
        }
    }

    override fun saveUserState(user: User, state: BotState<User>) {
        transaction {
            UserTable.update({ UserTable.botId eq user.botId }) {
                it[lastState] = state.serialize()
            }

            commit()
        }
    }

    override fun getUserAuth(user: User): CharArray {
        return transaction {
            UserDao.find {
                UserTable.botId eq user.botId
            }.limit(1).map { dao ->
                dao.auth?.toCharArray() ?: throw IllegalArgumentException("No 'auth' saved for user ${user.botId}")
            }.first()
        }
    }

    override fun getUserState(user: User): BotState<User> = transaction {
        val iterable = UserDao.find {
            UserTable.botId eq user.botId
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
                botId = user.botId
            }
            commit()
        }
    }
}
