package ru.jirabot.ui.terminal

import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.entities.User

class TerminalClient : Client {

    override fun sendMessage(user: User, text: String, buttons: List<List<Button>>?) {
        println("----")
        println(text)
        buttons?.forEach {
            print("/")
            it.forEach {
                print("${it.title}/")
            }
            println()
        }
        println("----")
    }

    override fun replaceMessage(user: User, messageId: Long, text: String, buttons: List<List<Button>>?) {
        println("REPLACED")
        sendMessage(user, text, buttons)
        println("REPLACED")
    }
}
