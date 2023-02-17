package ru.jirabot.domain.bot

abstract class RedirectBotState(
    messageId: Long? = null
) : BotState(messageId) {

    override fun obtainAction(action: UserAction): BotState {
        error("try obtain RedirectBotAction")
    }
}