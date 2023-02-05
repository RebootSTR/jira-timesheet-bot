package ru.jirabot.main.repository.sqlite

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.main.repository.sqlite.tables.UserDao
import ru.jirabot.main.repository.sqlite.tables.UserTable
import ru.jirabot.main.states.InitState

class SqliteUserRepository : UserRepository<User> {

    override fun saveUserAuth(user: User, auth: CharArray) {
        transaction {
            UserTable.update(
                { UserTable.botId eq user.botId }
            ) {
                it[UserTable.auth] = auth.toString()
            }

            commit()
        }
    }

    override fun saveUserState(user: User, state: BotState<User>) {
        transaction {
            UserTable.update(
                { UserTable.botId eq user.botId }
            ) {
                it[lastState] = Json.encodeToString(state)
            }

            commit()
        }
    }

    override fun getUserAuth(user: User): CharArray {
        return UserDao.find {
            UserTable.botId eq user.botId
        }.limit(1)
            .map { dao ->
                dao.auth.toCharArray()
            }.first()
    }

    override fun getUserState(user: User): BotState<User> {
        val iterable = UserDao.find {
            UserTable.botId eq user.botId
        }

        return if (iterable.empty()) {
            saveNewUser(user)
            InitState()
        } else {
            Json.decodeFromString<BotState<User>>(iterable.first().lastState)
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
