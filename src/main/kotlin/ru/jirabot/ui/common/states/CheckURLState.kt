package ru.jirabot.ui.common.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.usecase.CheckTaskURLUseCase

class CheckURLState(
    private val url: String
) : RedirectBotState() {

    override fun interactWithUser(user: User): BotState {
        val usecase = DI.get<CheckTaskURLUseCase>()
        return if (usecase(url)) {
            TaskNameInputState(url)
        } else {
            WrongURLState()
        }
    }
}
