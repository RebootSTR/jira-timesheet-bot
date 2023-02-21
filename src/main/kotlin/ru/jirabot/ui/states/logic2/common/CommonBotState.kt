package ru.jirabot.ui.states.logic2.common

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.serialization.Exclude

abstract class CommonBotState(
    messageId: Long?
): BotState(messageId) {

    @Exclude
    val dictionary: Dictionary = DI()

    override fun getClient(): Client = DI()
}