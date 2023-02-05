package ru.jirabot.telegram

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import ru.jirabot.domain.bot.Client

class TelegramClient(
    private val bot: Bot
): Client<TelegramUser> {

    override fun sendMessage(user: TelegramUser, text: String) {
        bot.sendMessage(
            chatId = ChatId.fromId(user.id),
            text = text
        )
    }
}