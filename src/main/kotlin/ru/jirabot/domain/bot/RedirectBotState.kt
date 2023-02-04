package ru.jirabot.domain.bot

import ru.jirabot.domain.UserAction

abstract class RedirectBotState: BotState() {

    override fun obtainAction(action: UserAction): BotState {
        error("try obtain RedirectBotAction")
    }
}