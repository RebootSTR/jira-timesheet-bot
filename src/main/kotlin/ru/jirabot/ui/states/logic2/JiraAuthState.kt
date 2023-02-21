package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class JiraAuthState(
    private val username: String,
    private val password: CharArray,
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {

    private val authUseCase: AuthUserUseCase = DI.get()

    // todo save login password if success ???
    override fun interactWithUser(user: User): BotState {
        val success = authUseCase(user, username, password)
        return if (success) {
            JiraAuthSuccess()
        } else {
            JiraAuthErrorState()
        }
    }
}
