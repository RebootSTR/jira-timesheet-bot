package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.TemplateRepository
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.common.CommonBotState
import ru.jirabot.ui.states.logic2.template.create.TaskNameInputState

class TemplateMenuState(
    messageId: Long? = null
) : CommonBotState(messageId) {

    @Exclude
    private val templateRepository: TemplateRepository = DI()

    override fun interactWithUser(user: User): BotState? {
        // todo add info to menu

        val templates = templateRepository.getTemplates(user)
            .map { listOf(Button(it.title, "?template_id=${it.id}")) } // todo usecase

        sendMessage(
            user = user,
            text = dictionary["TemplateMenuState"],
            buttons = templates + keyboard(),
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> {
                when (action.payload.toPayload()) {
                    Payloads.ADD -> TaskNameInputState(TemplateDraft(), messageId)
                    Payloads.BACK -> MenuState(messageId)
                    else -> parseCustomButton(action)
                }

            }

            is UserAction.Message -> {
                MenuState()
            }
        }

    private fun parseCustomButton(action: UserAction.ButtonClick): BotState {
        TODO()
    }

    private fun keyboard() = listOf(
        listOf(Payloads.ADD()),
        listOf(Payloads.BACK()),
    )
}
