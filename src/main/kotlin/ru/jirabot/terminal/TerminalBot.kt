package ru.jirabot.terminal

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.StateHandler
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.bot.BotState
import ru.jirabot.main.states.InitState
import ru.jirabot.domain.entities.User

object TerminalBot {

    fun run() {
        configureTerminal()

        var state: BotState<User> = InitState()
        while (true) {
            print("> ")
            val message = readln()
            state = obtainState(state, message)
        }
    }

    private fun obtainState(state: BotState<User>, message: String) =
        StateHandler.handleState(state, User(0), UserAction.Message(message)) {
            dictionary = DI.get()
            client = DI.get()
        }
}
