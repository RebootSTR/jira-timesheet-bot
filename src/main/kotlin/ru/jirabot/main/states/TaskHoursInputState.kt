package ru.jirabot.main.states

import ru.jirabot.domain.UserAction
import ru.jirabot.domain.bot.BotState

class TaskHoursInputState(
    private val url: String,
    private val name: String
): BotState() {

    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["TaskHoursInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> TODO()
        }
    }
}