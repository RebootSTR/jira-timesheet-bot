package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

class HelloState: BotState() {
    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["HelloState"]
        )
        return UsernameInputState()
    }

    override fun obtainAction(action: UserAction): BotState {
        TODO("Not yet implemented")
    }
}