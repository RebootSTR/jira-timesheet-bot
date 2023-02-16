package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload

class MenuState(
    private val messageId: Long? = null
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        // todo add info to menu
        client.sendMessage(
            user = user,
            text = dictionary["MenuState"],
            buttons = keyboard(),
            replaceMessageId = messageId
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> {
                when (action.payload.toPayload()) {
                    Payloads.TEMPLATES -> TemplateMenuState(action.messageId)
                    Payloads.TIME_SHEET -> TODO()
                    Payloads.SUBMIT -> TODO()
                    Payloads.SETTINGS -> TODO()
                    Payloads.INFO -> TODO()
                    else -> TODO()
                }
            }

            is UserAction.Message -> {
                TODO()
            }
        }

    private fun keyboard() = listOf(
        listOf(Payloads.TEMPLATES(), Payloads.TIME_SHEET()),
        listOf(Payloads.SUBMIT()),
        listOf(Payloads.SETTINGS()),
        listOf(Payloads.INFO()),
    )
}
