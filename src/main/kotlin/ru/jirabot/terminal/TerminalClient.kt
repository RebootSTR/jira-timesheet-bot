package ru.jirabot.terminal

import ru.jirabot.domain.Client

class TerminalClient: Client {
    override fun sendMessage(text: String) {
        println("----")
        println(text)
        println("----")
    }
}