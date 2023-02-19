package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.ui.drafts.TemplateDraft

class TaskHoursErrorState(
    private val template: TemplateDraft,
) : RedirectBotState() {

    override fun interactWithUser(user: User): BotState {
        client.sendMessage(
            user = user,
            text = dictionary["TaskHoursErrorState"],
        )
        return TaskHoursInputState(
            template = template.apply { hoursString = null },
            silent = true
        )
    }
}
