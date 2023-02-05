package ru.jirabot.main.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.telegram.TelegramUser

class JiraAuthState(
    private val username: String,
    private val password: String
) : RedirectBotState<TelegramUser>() {

    // todo save login password if success ???
    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        val authUseCase = DI.get<AuthUserUseCase>()
        val success = authUseCase(username, password)
        return if (success) {
            JiraAuthSuccess()
        } else {
            JiraAuthErrorState()
        }
    }
}