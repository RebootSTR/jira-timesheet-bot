package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.FillTimeDraft

class FillTimeErrorState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : BotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        messageId = client.sendMessage(
            user = user,
            text = dictionary["FillTimeErrorState"],
            buttons = keyboard(),
            replaceMessageId = messageId
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
