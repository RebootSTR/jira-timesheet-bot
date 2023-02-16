package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.TemplateDraft

class TaskNameInputState(
    private val template: TemplateDraft,
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        client.sendMessage(
            user = user,
            text = dictionary["TaskNameInputState"],
            buttons = keyboard()
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.CANCEL -> TemplateMenuState(action.messageId)
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
