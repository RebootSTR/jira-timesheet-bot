package ru.jirabot.ui.terminal

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.StateHandler
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.bot.BotState
import ru.jirabot.ui.common.states.InitState
import ru.jirabot.domain.entities.User

object TerminalBot {

    fun run() {
        configureTerminal()

        var state: BotState = InitState()
        while (true) {
            print("> ")
            val message = readln()
            state = if (message.startsWith("/")) {
                handleButton(state, message.substringAfter("/"))
            } else {
                handleMessage(state, message)
            }
        }
    }

    private fun handleMessage(state: BotState, message: String) =
        StateHandler.handleState(state, User(0), UserAction.Message(message)) {
            dictionary = DI.get()
            client = DI.get()
        }

    private fun handleButton(state: BotState, payload: String) =
        StateHandler.handleState(state, User(0), UserAction.ButtonClick(payload, 0L)) {
            dictionary = DI.get()
            client = DI.get()
        }
}
