package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.GetStatisticUseCase
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.states.logic2.common.CommonBotState

class MenuState(
    messageId: Long? = null
) : CommonBotState(messageId) {

    @Exclude
    private val statisticUseCase: GetStatisticUseCase = DI()

    override fun interactWithUser(user: User): BotState? {
        val text = dictionary["MenuState"]
        val statistic = statisticUseCase(user)

        sendMessage(
            user = user,
            text = text.format(statistic.name, statistic.templateCount, statistic.weekVisual),
            buttons = keyboard(),
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> {
                when (action.payload.toPayload()) {
                    Payloads.TEMPLATES -> TemplateMenuState(messageId)
                    Payloads.TIME_SHEET -> SelectFillTimeTypeState(messageId)
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
