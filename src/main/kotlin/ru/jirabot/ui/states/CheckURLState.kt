package ru.jirabot.ui.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.ui.drafts.TemplateDraft

class CheckURLState(
    private val template: TemplateDraft,
) : RedirectBotState() {

    @Exclude
    private val checkTaskURLUseCase: CheckTaskURLUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        return if (checkTaskURLUseCase(user, template.url)) {
            TaskHoursInputState(template)
        } else {
            WrongURLState(template)
        }
    }
}
