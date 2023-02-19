package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User

class JiraAuthSuccess : RedirectBotState() {
    override fun interactWithUser(user: User): BotState {
        client.sendMessage(
            user = user,
            text = dictionary["JiraAuthSuccess"]
        )
        return MenuState()
    }
}
