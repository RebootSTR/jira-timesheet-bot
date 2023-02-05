package ru.jirabot.main.states

import kotlinx.serialization.Serializable
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

@Serializable
class UsernameInputState : BotState<User>() {

    override fun interactWithUser(user: User): BotState<User>? {
        client.sendMessage(
            user = user,
            text = dictionary["UsernameInputState"],
            buttons = keyboard()
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState<User> =
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
