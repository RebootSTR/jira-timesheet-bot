package ru.jirabot.domain.bot

import ru.jirabot.domain.entities.User

object StateHandler {

    fun handleState(
        state: BotState,
        user: User,
        action: UserAction,
        injector: BotState.() -> Unit
    ): BotState {

        // Получаем новый стейт на основе текущего
        var newState: BotState? = state.obtainAction(action)
        while (true) {
            // Провайдим зависимости внутрь стейта
            newState!!.injector()
            // Проводим взаимодействие с пользователем и активируем логику
            val redirect = newState.interactWithUser(user)
            // если на прошлом этапе вернулся стейт, тогда редиректим на него (выполняем действия заного)
            if (redirect == null) {
                break
            } else {
                newState = redirect
            }
        }
        return newState!!
    }
}