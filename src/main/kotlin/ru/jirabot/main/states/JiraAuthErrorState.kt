package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

class JiraAuthErrorState: BotState() {

    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["JiraAuthErrorState"]
        )
        return UsernameInputState()
    }

    override fun obtainAction(action: UserAction): BotState {
        TODO("Not yet implemented")
    }
}