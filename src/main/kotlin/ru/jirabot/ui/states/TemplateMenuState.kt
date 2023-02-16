package ru.jirabot.ui.states

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.Button
import ru.jirabot.domain.bot.UserAction
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.TemplateRepository
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.ui.Payloads
import ru.jirabot.ui.Payloads.Companion.toPayload
import ru.jirabot.ui.drafts.TemplateDraft

class TemplateMenuState(
    private val messageId: Long? = null
) : BotState() {

    @Exclude
    private val templateRepository: TemplateRepository = DI()

    override fun interactWithUser(user: User): BotState? {
        // todo add info to menu

        val templates = templateRepository.getTemplates(user)
            .map { listOf( Button(it.title, "?template_id=${it.id}") ) }

        client.sendMessage(
            user = user,
            text = dictionary["TemplateMenuState"],
            buttons = templates + keyboard(),
            replaceMessageId = messageId
        )

        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> {
                when (action.payload.toPayload()) {
                    Payloads.ADD -> TaskNameInputState(TemplateDraft())
                    Payloads.BACK -> TODO()
                    else -> TODO()
                }
            }

            is UserAction.Message -> {
                TODO()
            }
        }

    private fun keyboard() = listOf(
        listOf(Payloads.ADD()),
        listOf(Payloads.BACK()),
    )
}
