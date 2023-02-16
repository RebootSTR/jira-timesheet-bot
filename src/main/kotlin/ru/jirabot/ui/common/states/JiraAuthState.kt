package ru.jirabot.ui.common.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.ui.common.User
import ru.jirabot.domain.usecase.AuthUserUseCase

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
