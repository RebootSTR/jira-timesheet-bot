package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.telegram.TelegramUser

class PasswordInputState(
    val username: String
) : BotState<TelegramUser>() {

    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        client.sendMessage(
            user = user,
            text = dictionary["PasswordInputState"]
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState<TelegramUser> =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                JiraAuthState(username, action.text)
            }
        }
}