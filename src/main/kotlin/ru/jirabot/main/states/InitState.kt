package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

/**
 * Начальное состояние, пользователь не общался с ботом ни разу
 */
@Serializable
class InitState() : BotState<User>() {

    // Никогда не будет вызвана
    override fun interactWithUser(user: User) = null

    override fun obtainAction(action: UserAction): BotState<User> {
        // Отправка сообщения с приветствием
        return UsernameInputState()
    }
}
