package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

@Serializable
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
