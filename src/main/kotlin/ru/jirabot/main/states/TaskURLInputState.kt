package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.telegram.TelegramUser

class TaskURLInputState(
    private val silent: Boolean = false
) : BotState<TelegramUser>() {

    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        if (!silent) {
            client.sendMessage(
                user = user,
                text = dictionary["TaskURLInputState"]
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState<TelegramUser> {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                return CheckURLState(action.text)
            }
        }
    }
}