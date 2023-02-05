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
import ru.jirabot.main.Credentials
import ru.jirabot.main.states.InitState

class TelegramBot {

    // todo реализация сохранения в базу (желательно с кэшированием но пока out of scope)
    private val stateStorage = mutableMapOf<TelegramUser, BotState<TelegramUser>>()
    private val bot = initBot()
    private val telegramClient = TelegramClient(bot)

    fun run() {
        bot.startPolling()
    }

    private fun initBot(): Bot = bot {
        logLevel = LogLevel.All()
        token = Credentials.TOKEN
        dispatch {
            // todo обработка кнопок
            text {
                // если юзер налл, то обрабатывать не нужно
                val userId = message.from?.id ?: return@text
                handleText(TelegramUser(userId), text)
            }
        }
    }

    private fun handleText(user: TelegramUser, text: String) {
        val state = stateStorage[user] ?: kotlin.run {
            InitState().apply {
                stateStorage[user] = this
            }
        }
        val newState = handleState(user, state, text)
        stateStorage[user] = newState
    }

    private fun handleState(user: TelegramUser, state: BotState<TelegramUser>, message: String) =
        StateHandler.handleState(state, user, UserAction.Message(message)) {
            dictionary = DI.get()
            client = telegramClient
        }
}