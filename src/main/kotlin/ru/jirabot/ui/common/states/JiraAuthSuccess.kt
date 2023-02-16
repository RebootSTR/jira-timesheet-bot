package ru.jirabot.ui.common.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User

class JiraAuthSuccess : RedirectBotState() {
    override fun interactWithUser(user: User): BotState {
        client.sendMessage(
            user = user,
            text = dictionary["JiraAuthSuccess"]
        )
        return FirstTemplateState()
    }
}
