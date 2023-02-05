package ru.jirabot.domain.bot

abstract class RedirectBotState<User> : BotState<User>() {

    override fun obtainAction(action: UserAction): BotState<User> {
        error("try obtain RedirectBotAction")
    }
}