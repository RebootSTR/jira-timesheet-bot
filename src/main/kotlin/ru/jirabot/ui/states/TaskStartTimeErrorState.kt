package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.drafts.TemplateDraft

class TaskStartTimeErrorState(
    private val template: TemplateDraft,
) : RedirectBotState() {

    override fun interactWithUser(user: User): BotState? {
        client.sendMessage(
            user = user,
            text = dictionary["TaskStartTimeErrorState"],
        )
        return TaskStartTimeInputState(
            template = template.apply { startTimeInMinutesString = null },
            silent = true
        )
    }
}
