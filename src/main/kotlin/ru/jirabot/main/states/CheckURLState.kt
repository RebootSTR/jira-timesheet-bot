package ru.jirabot.main.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.telegram.TelegramUser

class CheckURLState(
    private val url: String
) : RedirectBotState<TelegramUser>() {

    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        val usecase = DI.get<CheckTaskURLUseCase>()
        return if (usecase(url)) {
            TaskNameInputState(url)
        } else {
            WrongURLState()
        }
    }
}