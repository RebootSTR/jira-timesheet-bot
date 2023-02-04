package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

class UsernameInputState: BotState() {

    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["UsernameInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                PasswordInputState(username = action.text)
            }
        }
}