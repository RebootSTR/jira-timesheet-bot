package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.drafts.TemplateDraft

class TaskHoursInputState(
    private val template: TemplateDraft,
    private val silent: Boolean = false
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        if (!silent) {
            client.sendMessage(
                user = user,
                text = dictionary["TaskHoursInputState"]
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> HoursValidateState(
                template = template.apply { hoursString = action.text }
            )
        }

}
