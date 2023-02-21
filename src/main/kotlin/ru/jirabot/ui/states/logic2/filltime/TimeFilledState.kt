package ru.jirabot.ui.states.logic2.filltime

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.User
import ru.jirabot.ui.drafts.FillTimeDraft
import ru.jirabot.ui.states.logic2.MenuState
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class TimeFilledState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {

    override fun interactWithUser(user: User): BotState {
        // todo нужно описание
        sendMessage(
            user = user,
            text = dictionary["TimeFilledState"],
        )
        return MenuState()
    }
}
