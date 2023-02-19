package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.FillTimeDraft
import ru.jirabot.ui.drafts.FillTimeType

class SelectFillTimeTypeState(
    messageId: Long? = null
) : BotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        messageId = client.sendMessage(
            user = user,
            text = dictionary["SelectFillTimeTypeState"],
            buttons = keyboard(),
            replaceMessageId = messageId
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.ALL_WEEK_TYPE -> TODO()
                Payloads.ONE_DAY_TYPE -> SelectTemplateState(
                    draft = FillTimeDraft(fillTimeType = FillTimeType.ONE_DAY),
                    messageId = messageId
                )
                Payloads.BACK -> MenuState(messageId)

                else -> TODO()
            }

            is UserAction.Message -> TODO()
        }

    private fun keyboard() = listOf(
        listOf(Payloads.ALL_WEEK_TYPE(), Payloads.ONE_DAY_TYPE()),
        listOf(Payloads.BACK())
    )
}
