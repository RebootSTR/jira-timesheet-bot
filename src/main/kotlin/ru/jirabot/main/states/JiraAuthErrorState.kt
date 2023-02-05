package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.telegram.TelegramUser

class JiraAuthErrorState : RedirectBotState<TelegramUser>() {

    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        client.sendMessage(
            user = user,
            text = dictionary["JiraAuthErrorState"]
        )
        return UsernameInputState()
    }
}