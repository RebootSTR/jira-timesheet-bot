package ru.jirabot.ui.common.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User

class FirstTemplateState : RedirectBotState() {

    override fun interactWithUser(user: User): BotState {
        client.sendMessage(
            user = user,
            text = dictionary["FirstTemplateState"]
        )
        return TaskURLInputState()
    }
}
