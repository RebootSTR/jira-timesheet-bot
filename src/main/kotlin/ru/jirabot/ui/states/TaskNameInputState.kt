package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.drafts.TemplateDraft

class TaskNameInputState(
    private val template: TemplateDraft,
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        client.sendMessage(
            user = user,
            text = dictionary["TaskNameInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                return TaskURLInputState(
                    template = template.apply { title = action.text }
                )
            }
        }
    }
}
