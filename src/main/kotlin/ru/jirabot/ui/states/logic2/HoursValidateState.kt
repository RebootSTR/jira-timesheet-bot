package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.ParseHoursInputUseCase
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class HoursValidateState(
    private val template: TemplateDraft,
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {

    @Exclude
    private val parser: ParseHoursInputUseCase = DI()

    override fun interactWithUser(user: User): BotState {
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
