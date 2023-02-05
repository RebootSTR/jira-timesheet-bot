package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.telegram.TelegramUser

class UsernameInputState : BotState<TelegramUser>() {

    override fun interactWithUser(user: TelegramUser): BotState<TelegramUser>? {
        client.sendMessage(
            user = user,
            text = dictionary["UsernameInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState<TelegramUser> =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                PasswordInputState(username = action.text)
            }
        }
}