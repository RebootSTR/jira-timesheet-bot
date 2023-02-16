package ru.jirabot.ui.states

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User

/**
 * Стейт для тестирования кнопок и редактирования сообщения.
 * Сделан УЖАСНО, НЕ ПОВТОРЯТь, для каждого нового изменения нужнен свой стейт, иначе жопа нерасширяемая будет
 */
class TesterInputState(
    private val messageText: String? = null,
    private val messageId: Long? = null
) : BotState() {

    override fun interactWithUser(user: User): BotState? {
        client.sendMessage(
            user = user,
            text = messageText ?: dictionary["TesterInputState"],
            buttons = keyboard(),
            replaceMessageId = messageId
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> {
                when (action.payload) {
                    Payloads.FIRST.name,
                    Payloads.SECOND.name,
                    Payloads.THIRD.name -> TesterInputState(
                        "Нажата кнопка: ${action.payload}",
                        action.messageId
                    )

                    else -> this
                }
            }

            is UserAction.Message -> {
                TODO()
            }
        }

    private fun keyboard() = listOf(
        listOf(Button("Первая", Payloads.FIRST.name), Button("Вторая", Payloads.SECOND.name)),
        listOf(Button("Третья", Payloads.THIRD.name))
    )

    private enum class Payloads {
        FIRST,
        SECOND,
        THIRD
    }
}
