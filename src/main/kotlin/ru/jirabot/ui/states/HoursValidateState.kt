package ru.jirabot.ui.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.ParseHoursInputUseCase
import ru.jirabot.ui.drafts.TemplateDraft

class HoursValidateState(
    private val template: TemplateDraft,
) : RedirectBotState() {

    @Exclude
    private val parser: ParseHoursInputUseCase = DI()

    override fun interactWithUser(user: User): BotState? {
        val hours = parser(template.hoursString)

        return if (hours != null) {
            TaskStartTimeInputState(
                template = template.apply { this.hours = hours }
            )
        } else {
            TaskHoursErrorState(template)
        }
    }
}
