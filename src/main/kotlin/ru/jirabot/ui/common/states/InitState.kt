package ru.jirabot.ui.common.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.ui.common.User

/**
 * Начальное состояние, пользователь не общался с ботом ни разу
 */
class InitState() : BotState<User>() {

    // Никогда не будет вызвана
    override fun interactWithUser(user: User) = null

    override fun obtainAction(action: UserAction): BotState<User> {
        // Отправка сообщения с приветствием
        return UsernameInputState()
    }
}
