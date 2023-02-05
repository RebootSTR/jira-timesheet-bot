package ru.jirabot.domain.bot

object StateHandler {

    fun <User> handleState(
        state: BotState<User>,
        user: User,
        action: UserAction,
        injector: BotState<User>.() -> Unit
    ): BotState<User> {

        var newState: BotState<User>? = state.obtainAction(action)
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