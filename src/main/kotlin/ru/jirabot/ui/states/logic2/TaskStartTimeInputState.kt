package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.common.CommonBotState

class TaskStartTimeInputState(
    private val template: TemplateDraft,
    private val silent: Boolean = false,
    messageId: Long? = null,
) : CommonBotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        if (!silent) {
            sendMessage(
                user = user,
                text = dictionary["TaskStartTimeInputState"],
                buttons = keyboard(),
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.CANCEL -> TemplateMenuState(messageId)
                else -> TODO()
            }

            is UserAction.Message -> TaskStartTimeValidateState(
                template = template.apply { startTimeInMinutesString = action.text }
            )
        }

    private fun keyboard() = listOf(
        listOf(Payloads.CANCEL()),
    )
}
