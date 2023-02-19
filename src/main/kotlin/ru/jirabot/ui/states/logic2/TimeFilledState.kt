package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.ui.drafts.FillTimeDraft

class TimeFilledState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : RedirectBotState(messageId) {

    override fun interactWithUser(user: User): BotState {
        // todo нужно описание
        messageId = client.sendMessage(
            user = user,
            text = dictionary["TimeFilledState"],
        )
        return MenuState()
    }
}
