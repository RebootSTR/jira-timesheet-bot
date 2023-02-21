package ru.jirabot.ui.states.logic2.filltime

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.FillTimeDraft
import ru.jirabot.ui.states.logic2.MenuState
import ru.jirabot.ui.states.logic2.common.CommonBotState

class FillTimeErrorState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : CommonBotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        sendMessage(
            user = user,
            text = dictionary["FillTimeErrorState"],
            buttons = keyboard(),
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.REPEAT -> TryFillTimeState(draft, messageId)
                Payloads.CANCEL -> MenuState(messageId)
                else -> TODO()
            }

            is UserAction.Message -> TODO()
        }

    private fun keyboard() = listOf(
        listOf(Payloads.REPEAT()),
        listOf(Payloads.CANCEL())
    )
}
