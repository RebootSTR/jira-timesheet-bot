package ru.jirabot.main.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction
import ru.jirabot.domain.usecase.AuthUserUseCase

class JiraAuthState(
    private val username: String,
    private val password: String
): BotState() {

    // todo save login password if success ???
    override fun interactWithUser(): BotState? {
        val authUseCase = DI.get<AuthUserUseCase>()
        val success = authUseCase(username, password)
        return if (success) {
            JiraAuthSuccess()
        } else {
            JiraAuthErrorState()
        }
    }

    override fun obtainAction(action: UserAction): BotState {
        TODO("Not yet implemented")
    }
}