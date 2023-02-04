package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState

class WrongURLState : RedirectBotState() {

    override fun interactWithUser(): BotState? {
        client.sendMessage(
            text = dictionary["WrongURLState"]
        )
        return TaskURLInputState(silent = true)
    }

}