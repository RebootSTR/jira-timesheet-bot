package ru.jirabot.domain.bot

abstract class RedirectBotState : BotState() {

    override fun obtainAction(action: UserAction): BotState {
        error("try obtain RedirectBotAction")
    }
}