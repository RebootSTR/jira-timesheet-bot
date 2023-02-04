package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

class JiraAuthSuccess: BotState() {
    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["JiraAuthSuccess"]
        )
        return FirstTemplateState()
    }

    override fun obtainAction(action: UserAction): BotState {
        TODO("Not yet implemented")
    }
}