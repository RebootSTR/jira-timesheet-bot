package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

class FirstTemplateState: BotState() {

    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["FirstTemplateState"]
        )
        return TaskURLInputState()
    }

    override fun obtainAction(action: UserAction): BotState {
        TODO("Not yet implemented")
    }
}