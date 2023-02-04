package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

class TaskURLInputState(
    private val silent: Boolean = false
): BotState() {

    override fun interactWithUser(): BotState? {
        if (!silent) {
            client.sendMessage(
                text = dictionary["TaskURLInputState"]
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                return CheckURLState(action.text)
            }
        }
    }
}