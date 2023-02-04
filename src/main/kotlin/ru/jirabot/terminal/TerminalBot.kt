package ru.jirabot.terminal

import ru.jirabot.di.DI
import ru.jirabot.domain.UserAction
import ru.jirabot.domain.bot.BotState
import ru.jirabot.main.states.InitState

object TerminalBot {

    fun run() {
        configureTerminal()

        var state: BotState = InitState()
        while (true) {
            print("> ")
            val message = readln()
            state = obtainState(state, message)
        }
    }

    private fun obtainState(state: BotState, message: String): BotState {
        var newState: BotState? = state.obtainAction(UserAction.Message(message))
        while (true) {
            newState!!.injectDi()
            val redirect = newState.interactWithUser()
            if (redirect == null) {
                break
            } else {
                newState = redirect
            }
        }
        return newState!!
    }

    private fun BotState.injectDi() {
        dictionary = DI.get()
        client = DI.get()
    }
}