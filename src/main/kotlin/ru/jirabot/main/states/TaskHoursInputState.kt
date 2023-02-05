package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.telegram.TelegramUser

class TaskHoursInputState(
    private val url: String,
    private val name: String
) : BotState<TelegramUser>() {

    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        client.sendMessage(
            user = user,
            text = dictionary["TaskHoursInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState<TelegramUser> {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> TODO()
        }
    }
}