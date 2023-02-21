package ru.jirabot.ui.telegram

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.logging.LogLevel
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.StateHandler
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository

class TelegramBot {

    private val userRepository: UserRepository = DI()
    private val settingsRepository: SettingsRepository = DI()

    private val bot = initBot()
    private val telegramClient = TelegramClient(bot)

    fun run() {
        bot.startPolling()
    }

    private fun initBot(): Bot = bot {
        logLevel = LogLevel.All()
        token = settingsRepository.getSettingsValue(Settings.TG_TOKEN)
        dispatch {
            text {
                // если юзер налл, то обрабатывать не нужно
                val userId = message.from?.id ?: return@text
                handleText(User(userId), text)
            }
            this.callbackQuery {
                val userId = callbackQuery.from.id
                val messageId = callbackQuery.message?.messageId
                handleButton(User(userId), callbackQuery.data, messageId)
            }
        }
    }

    private fun handleText(user: User, text: String) {
        val state = userRepository.getUserState(user)
        val newState = StateHandler.handleState(state, user, UserAction.Message(text), ::injector) ?: return
        userRepository.saveUserState(user, newState)
    }

    private fun handleButton(user: User, payload: String, messageId: Long?) {
        val state = userRepository.getUserState(user)
        val newState =
            StateHandler.handleState(state, user, UserAction.ButtonClick(payload, messageId), ::injector) ?: return
        userRepository.saveUserState(user, newState)
    }

    private fun injector(state: BotState) {
        state.dictionary = DI.get()
        state.client = telegramClient
    }
}
