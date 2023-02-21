package ru.jirabot.ui.states.logic2.common

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.dictionary.Dictionary

abstract class CommonBotState(
    messageId: Long?
): BotState(messageId) {

    val dictionary: Dictionary = DI()

    override fun getClient(): Client = DI()
}