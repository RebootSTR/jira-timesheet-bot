package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.states.logic2.common.CommonBotState

/**
 * Начальное состояние, пользователь не общался с ботом ни разу
 */
class InitState(
    messageId: Long? = null
) : CommonBotState(messageId) {

    // Никогда не будет вызвана
    override fun interactWithUser(user: User) = null

    override fun obtainAction(action: UserAction): BotState {
        // Отправка сообщения с приветствием
        return CheckUserState()
    }
}
