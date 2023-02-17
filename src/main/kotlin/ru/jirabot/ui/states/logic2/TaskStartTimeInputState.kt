package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.TemplateDraft

class TaskStartTimeInputState(
    private val template: TemplateDraft,
    private val silent: Boolean = false
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        if (!silent) {
            client.sendMessage(
                user = user,
                text = dictionary["TaskStartTimeInputState"],
                buttons = keyboard()
            )
        }
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.CANCEL -> TemplateMenuState(action.messageId)
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
