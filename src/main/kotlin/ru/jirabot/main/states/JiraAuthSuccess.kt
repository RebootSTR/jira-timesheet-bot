package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User

class JiraAuthSuccess : RedirectBotState<User>() {
    override fun interactWithUser(user: User): BotState<User>? {
        client.sendMessage(
            user = user,
            text = dictionary["JiraAuthSuccess"]
        )
        return FirstTemplateState()
    }
}
