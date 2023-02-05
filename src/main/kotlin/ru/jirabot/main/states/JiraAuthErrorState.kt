package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User

@Serializable
class JiraAuthErrorState : RedirectBotState<User>() {

    override fun interactWithUser(user: User): BotState<User>? {
        client.sendMessage(
            user = user,
            text = dictionary["JiraAuthErrorState"]
        )
        return UsernameInputState()
    }
}
