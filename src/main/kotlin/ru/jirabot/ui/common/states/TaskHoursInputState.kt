package ru.jirabot.ui.common.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.ui.common.User

class TaskHoursInputState(
    private val url: String,
    private val name: String
) : BotState<User>() {

    override fun interactWithUser(user: User): BotState<User>? {
        client.sendMessage(
            user = user,
            text = dictionary["TaskHoursInputState"]
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState<User> {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> TODO()
        }
    }
}
