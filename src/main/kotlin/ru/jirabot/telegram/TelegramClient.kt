package ru.jirabot.telegram

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.entities.User

class TelegramClient(
    private val bot: Bot
): Client<User> {

    override fun sendMessage(user: User, text: String) {
        bot.sendMessage(
            chatId = ChatId.fromId(user.botId),
            text = text
        )
    }
}
