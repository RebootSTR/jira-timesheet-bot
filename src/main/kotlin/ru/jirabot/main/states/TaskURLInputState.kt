package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

@Serializable
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
