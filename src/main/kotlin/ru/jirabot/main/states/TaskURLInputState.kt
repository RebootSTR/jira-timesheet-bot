package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

class TaskURLInputState(
    private val silent: Boolean = false
) : BotState<User>() {

    override fun interactWithUser(user: User): BotState<User>? {
        if (!silent) {
            client.sendMessage(
                user = user,
                text = dictionary["TaskURLInputState"]
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState<User> {
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                return CheckURLState(action.text)
            }
        }
    }
}
