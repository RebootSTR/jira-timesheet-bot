package ru.jirabot.ui.common.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.ui.common.User

class HelloState : RedirectBotState<User>() {
    override fun interactWithUser(user: User): BotState<User>? {
        client.sendMessage(
            user = user,
            text = dictionary["HelloState"]
        )
        return UsernameInputState()
    }
}
