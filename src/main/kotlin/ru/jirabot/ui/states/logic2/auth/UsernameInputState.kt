package ru.jirabot.ui.states.logic2.auth

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.ui.states.logic2.common.CommonBotState

class UsernameInputState(
    messageId: Long? = null,
) : CommonBotState(messageId) {

    override fun interactWithUser(user: User): BotState? {
        sendMessage(
            user = user,
            text = dictionary["UsernameInputState"],
            buttons = keyboard()
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> TODO()
            is UserAction.Message -> {
                PasswordInputState(username = action.text)
            }
        }

    private fun keyboard() = listOf(
        listOf(Button("Я у мамы тестировщик", Payloads.TESTER.name))
    )

    private enum class Payloads {
        TESTER
    }
}
