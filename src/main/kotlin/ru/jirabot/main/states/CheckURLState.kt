package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.domain.entities.User

@Serializable
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
