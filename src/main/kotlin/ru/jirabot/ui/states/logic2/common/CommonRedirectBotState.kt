package ru.jirabot.ui.states.logic2.common

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.serialization.Exclude

abstract class CommonRedirectBotState(
    messageId: Long?
): RedirectBotState(messageId) {

    @Exclude
    val dictionary: Dictionary = DI()

    override fun getClient(): Client = DI()
}