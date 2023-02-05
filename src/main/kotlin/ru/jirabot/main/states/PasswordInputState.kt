package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

class PasswordInputState(
    val username: String
) : BotState<User>() {

    override fun interactWithUser(user: User): BotState<User>? {
        client.sendMessage(
            user = user,
            text = dictionary["PasswordInputState"]
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState<User> =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                JiraAuthState(username, action.text.toCharArray())
            }
        }
}
