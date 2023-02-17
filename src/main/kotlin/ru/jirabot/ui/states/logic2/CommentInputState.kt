package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.FillTimeDraft

class CommentInputState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : BotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        messageId = client.sendMessage(
            user = user,
            // todo сделать опсиание
            text = dictionary["CommentInputState"],
            buttons = keyboard(),
            replaceMessageId = messageId
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.BACK -> TODO()
                else -> TODO()
            }

            is UserAction.Message -> TryFillTimeState(
                draft = draft.apply {
                    comment = action.text
                }
            )
        }

    private fun keyboard() = listOf(
        listOf(Payloads.BACK())
    )
}
