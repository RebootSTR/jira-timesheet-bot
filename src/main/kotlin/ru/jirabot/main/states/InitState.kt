package ru.jirabot.main.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.telegram.TelegramUser

/**
 * Начальное состояние, пользователь не общался с ботом ни разу
 */
class InitState() : BotState<TelegramUser>() {

    // Никогда не будет вызвана
    override fun interactWithUser(user: TelegramUser) = null

    override fun obtainAction(action: UserAction): BotState<TelegramUser> {
        // Отправка сообщения с приветствием
        return UsernameInputState()
    }
}