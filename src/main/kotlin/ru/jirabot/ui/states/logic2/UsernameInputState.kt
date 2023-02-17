package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

class UsernameInputState : BotState() {

    override fun interactWithUser(user: User): BotState? {
        client.sendMessage(
            user = user,
            text = dictionary["UsernameInputState"],
            buttons = keyboard()
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> {
                when (action.payload) {
                    Payloads.TESTER.name -> {
                        TesterInputState(null, null)
                    }
                    else -> this
                }
            }
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
