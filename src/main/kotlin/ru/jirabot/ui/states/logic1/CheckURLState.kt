package ru.jirabot.ui.states.logic1

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.usecase.CheckTaskURLUseCase

// todo многомодульно вытащить все логики
class CheckURLState(
    private val url: String
) : RedirectBotState() {

    override fun interactWithUser(user: User): BotState {
        val usecase = DI.get<CheckTaskURLUseCase>()
        return if (usecase(user, url)) {
            TaskNameInputState(url)
        } else {
            WrongURLState()
        }
    }
}
