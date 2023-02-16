package ru.jirabot.data.utils

import com.google.gson.Gson
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState

object Serializer {

    private val gson: Gson = DI()

    fun BotState.serialize(): String {
        return gson.toJson(this)
    }

    fun String.deserializeBotState(): BotState {
        return gson.fromJson(this, BotState::class.java)
    }
}