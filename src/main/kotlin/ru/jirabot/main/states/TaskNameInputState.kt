package ru.jirabot.main.states

import ru.jirabot.domain.UserAction
import ru.jirabot.domain.bot.BotState

class TaskNameInputState(
    private val url: String
): BotState() {

    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["TaskNameInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                return TaskHoursInputState(
                    url = url,
                    name = action.text
                )
            }
        }
    }
}