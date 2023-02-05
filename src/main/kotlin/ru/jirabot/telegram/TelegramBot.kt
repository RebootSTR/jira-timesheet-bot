package ru.jirabot.telegram

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.logging.LogLevel
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.StateHandler
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository

class TelegramBot {

    private val userRepository: UserRepository<User> = DI()
    private val settingsRepository: SettingsRepository = DI()

    private val bot = initBot()
    private val telegramClient = TelegramClient(bot)

    fun run() {
        bot.startPolling()
    }

    private fun initBot(): Bot = bot {
        logLevel = LogLevel.All()
        token = settingsRepository.getSettingsValue(Settings.TG_TOKEN)
        proxy
        dispatch {
            // todo обработка кнопок
            text {
                // если юзер налл, то обрабатывать не нужно
                val userId = message.from?.id ?: return@text
                handleText(User(userId), text)
            }
        }
    }

    private fun handleText(user: User, text: String) {
        val state = userRepository.getUserState(user)
        val newState = handleState(user, state, text)
        userRepository.saveUserState(user, newState)
    }

    private fun handleState(user: User, state: BotState<User>, message: String) =
        StateHandler.handleState(state, user, UserAction.Message(message)) {
            dictionary = DI.get()
            client = telegramClient
        }
}
