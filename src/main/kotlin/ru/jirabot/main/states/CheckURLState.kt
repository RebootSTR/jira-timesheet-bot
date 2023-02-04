package ru.jirabot.main.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction
import ru.jirabot.domain.usecase.CheckTaskURLUseCase

class CheckURLState(
    private val url: String
): BotState() {

    override fun interactWithUser(): BotState? {
        val usecase = DI.get<CheckTaskURLUseCase>()
        return if (usecase(url)) {
            TaskNameInputState(url)
        } else {
            WrongURLState()
        }
    }

    override fun obtainAction(action: UserAction): BotState {
        TODO("Not yet implemented")
    }
}