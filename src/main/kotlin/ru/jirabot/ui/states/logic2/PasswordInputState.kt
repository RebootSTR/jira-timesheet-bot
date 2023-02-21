package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.states.logic2.common.CommonBotState

class PasswordInputState(
    val username: String,
    messageId: Long? = null
) : CommonBotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        sendMessage(
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
