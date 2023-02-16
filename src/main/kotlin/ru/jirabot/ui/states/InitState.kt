package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

/**
 * Начальное состояние, пользователь не общался с ботом ни разу
 */
class InitState() : BotState() {

    // Никогда не будет вызвана
    override fun interactWithUser(user: User) = null

    override fun obtainAction(action: UserAction): BotState {
        // Отправка сообщения с приветствием
        return CheckUserState()
    }
}
