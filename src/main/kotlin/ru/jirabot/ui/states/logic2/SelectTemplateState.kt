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
import ru.jirabot.ui.drafts.FillTimeDraft
import ru.jirabot.ui.states.logic2.common.CommonBotState

class SelectTemplateState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : CommonBotState(messageId) {

    @Exclude
    private val templateRepository: TemplateRepository = DI()

    override fun interactWithUser(user: User): BotState? {
        val templates = templateRepository.getTemplates(user)
            .map { listOf(Button(it.title, PREFIX + it.id)) } // todo usecase

        sendMessage(
            user = user,
            text = dictionary["SelectTemplateState"],
            buttons = templates + keyboard(),
        )
        return null
    }

    override fun obtainAction(action: UserAction): BotState =
        when (action) {
            is UserAction.ButtonClick -> when (action.payload.toPayload()) {
                Payloads.BACK -> TODO()
                else -> {
                    val template = action.payload.drop(PREFIX.length).toLong() // todo need exception avoid mb
                    CommentInputState(
                        draft = draft.apply {
                            templateId = template
                        },
                        messageId = messageId
                    )
                }
            }

            is UserAction.Message -> TODO()
        }

    private fun keyboard() = listOf(
        listOf(Payloads.BACK())
    )

    companion object {
        private const val PREFIX = "?template_id="
    }
}
