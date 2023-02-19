package ru.jirabot.ui.telegram

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.model.User

class TelegramClient(
    private val bot: Bot
) : Client {

    override fun sendMessage(user: User, text: String, replaceMessageId: Long?, buttons: List<List<Button>>?): Long? {
        var messageId: Long? = null
        if (replaceMessageId == null) {
            val message = bot.sendMessage(
                chatId = ChatId.fromId(user.userId),
                text = text,
                replyMarkup = buttons?.keyboard()
            )
            if (message.isSuccess) {
                messageId = message.get().messageId
            }
        } else {
            bot.editMessageText(
                chatId = ChatId.fromId(user.userId),
                messageId = replaceMessageId,
                text = text,
                replyMarkup = buttons?.keyboard()
            )
            messageId = replaceMessageId
        }
        return messageId
    }

    private fun List<List<Button>>.keyboard() = InlineKeyboardMarkup.create(
        this.map {
            it.map { button ->
                InlineKeyboardButton.CallbackData(button.title, button.payload)
            }
        }
    )
}
