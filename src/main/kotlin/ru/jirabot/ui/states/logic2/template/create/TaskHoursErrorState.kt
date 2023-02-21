package ru.jirabot.ui.states.logic2.template.create

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.User
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class TaskHoursErrorState(
    private val template: TemplateDraft,
    messageId: Long? = null,
) : CommonRedirectBotState(messageId) {

    override fun interactWithUser(user: User): BotState {
        sendMessage(
            user = user,
            text = dictionary["TaskHoursErrorState"],
        )
        return TaskHoursInputState(
            template = template.apply { hoursString = null },
            silent = true
        )
    }
}
