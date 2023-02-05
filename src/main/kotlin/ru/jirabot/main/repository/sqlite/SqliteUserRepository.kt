package ru.jirabot.main.repository.sqlite

import com.atlassian.jira.rest.client.api.domain.Project
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.main.repository.sqlite.tables.UserDao
import ru.jirabot.main.repository.sqlite.tables.UserTable
import ru.jirabot.main.states.*
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class SqliteUserRepository : UserRepository<User> {

    private val module = SerializersModule {
        for (state in getAllStates()) {
            polymorphic(BotState::class) {
                subclass(state)
            }
        }
    }

    private fun getAllStates() = arrayOf(
        CheckURLState::class,
        FirstTemplateState::class,
        HelloState::class,
        InitState::class,
        JiraAuthSuccess::class,
        JiraAuthState::class,
        PasswordInputState::class,
        TaskHoursInputState::class,
        TaskNameInputState::class,
        TaskURLInputState::class,
        UsernameInputState::class,
        WrongURLState::class
    )

    private val format = Json {
        serializersModule = module
    }

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
                it[lastState] = format.encodeToString(state)
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
            format.decodeFromString<BotState<User>>(iterable.first().lastState!!)
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
