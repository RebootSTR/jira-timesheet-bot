package ru.jirabot.terminal

import ru.jirabot.domain.bot.Client
import ru.jirabot.telegram.TelegramUser

class TerminalClient : Client<TelegramUser> {

    override fun sendMessage(user: TelegramUser, text: String) {
        println("----")
        println(text)
        println("----")
    }
}