package ru.jirabot.ui.states.logic1

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User

class HelloState : RedirectBotState() {
    override fun interactWithUser(user: User): BotState {
        client.sendMessage(
            user = user,
            text = dictionary["HelloState"]
        )
        return UsernameInputState()
    }
}
