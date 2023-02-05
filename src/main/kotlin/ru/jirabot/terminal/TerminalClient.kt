package ru.jirabot.terminal

import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.entities.User

class TerminalClient : Client<User> {

    override fun sendMessage(user: User, text: String) {
        println("----")
        println(text)
        println("----")
    }
}
