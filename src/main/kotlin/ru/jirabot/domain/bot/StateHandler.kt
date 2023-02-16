package ru.jirabot.domain.bot

import ru.jirabot.domain.entities.User

object StateHandler {

    fun handleState(
        state: BotState,
        user: User,
        action: UserAction,
        injector: BotState.() -> Unit
    ): BotState {

        var newState: BotState? = state.obtainAction(action)
        while (true) {
            newState!!.injector()
            val redirect = newState.interactWithUser(user)
            if (redirect == null) {
                break
            } else {
                newState = redirect
            }
        }
        return newState!!
    }
}