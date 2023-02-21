package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class JiraAuthSuccess(
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {
    override fun interactWithUser(user: User): BotState {
        sendMessage(
            user = user,
            text = dictionary["JiraAuthSuccess"],
        )
        return MenuState()
    }
}
