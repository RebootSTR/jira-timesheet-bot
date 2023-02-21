package ru.jirabot.ui.states.logic2.template.create

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.ParseStartTimeInputUseCase
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class TaskStartTimeValidateState(
    private val template: TemplateDraft,
    messageId: Long? = null,
) : CommonRedirectBotState(messageId) {

    @Exclude
    private val parser: ParseStartTimeInputUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        val result = parser(template.startTimeInMinutesString)
        return if (result == null) {
            TaskStartTimeErrorState(template)
        } else {
            SaveTemplateState(
                template = template.apply { this.startTimeInMinutes = result }
            )
        }
    }
}
