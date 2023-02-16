package ru.jirabot.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState

object Serializer {

    private val gson: Gson = DI()

    fun BotState<*>.serialize(): String {
        return gson.toJson(this)
    }

    fun <T> String.deserializeBotState(): BotState<T> {
        val type = object : TypeToken<BotState<T>>() {}.type;
        return gson.fromJson(this, type)
    }
}