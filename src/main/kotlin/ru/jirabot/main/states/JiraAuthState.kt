package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.entities.User

@Serializable
class JiraAuthState(
    private val username: String,
    private val password: CharArray
) : RedirectBotState<User>() {

    private val authUseCase: AuthUserUseCase<User> = DI.get()
    // todo save login password if success ???
    override fun interactWithUser(user: User): BotState<User>? {
        val success = authUseCase(user, username, password)
        return if (success) {
            JiraAuthSuccess()
        } else {
            JiraAuthErrorState()
        }
    }
}
