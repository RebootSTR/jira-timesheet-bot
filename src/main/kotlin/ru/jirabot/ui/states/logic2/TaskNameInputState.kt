package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.TemplateDraft

class TaskNameInputState(
    private val template: TemplateDraft,
    messageId: Long? = null
) : BotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        messageId = client.sendMessage(
            user = user,
            text = dictionary["TaskNameInputState"],
            buttons = keyboard(),
            replaceMessageId = messageId
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.CANCEL -> TemplateMenuState(messageId)
                else -> TODO()
            }

            is UserAction.Message -> {
                TaskURLInputState(
                    template = template.apply { title = action.text }
                )
            }
        }

    private fun keyboard() = listOf(
        listOf(Payloads.CANCEL()),
    )
}
