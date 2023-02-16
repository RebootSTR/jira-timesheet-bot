package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.drafts.TemplateDraft

class TaskStartTimeInputState(
    private val template: TemplateDraft,
    private val silent: Boolean = false
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        if (!silent) {
            client.sendMessage(
                user = user,
                text = dictionary["TaskStartTimeInputState"]
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> TaskStartTimeValidateState(
                template = template.apply { startTimeInMinutesString = action.text }
            )
        }

}
