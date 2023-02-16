package ru.jirabot.ui.common.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.ui.common.User
import ru.jirabot.domain.usecase.CheckTaskURLUseCase

class CheckURLState(
    private val url: String
) : RedirectBotState<User>() {

    override fun interactWithUser(user: User): BotState<User>? {
        val usecase = DI.get<CheckTaskURLUseCase>()
        return if (usecase(url)) {
            TaskNameInputState(url)
        } else {
            WrongURLState()
        }
    }
}
