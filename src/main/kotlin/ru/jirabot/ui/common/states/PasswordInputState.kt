package ru.jirabot.ui.common.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

class PasswordInputState(
    val username: String
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        client.sendMessage(
            user = user,
            text = dictionary["PasswordInputState"]
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                JiraAuthState(username, action.text.toCharArray())
            }
        }
}
