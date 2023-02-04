package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.UserAction

/**
 * Начальное состояние, пользователь не общался с ботом ни разу
 */
class InitState(): BotState() {

    // Никогда не будет вызвана
    override fun interactWithUser() = null

    override fun obtainAction(action: UserAction): BotState {
        // Отправка сообщения с приветствием
        return UsernameInputState()
    }
}