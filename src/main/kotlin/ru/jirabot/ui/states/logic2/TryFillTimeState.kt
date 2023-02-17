package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.FillTimeUseCase
import ru.jirabot.ui.drafts.FillTimeDraft

class TryFillTimeState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : RedirectBotState(messageId) {

    @Exclude
    private val fillTimeUseCase: FillTimeUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        messageId = client.sendMessage(
            user = user,
            text = dictionary["TryFillTimeState"],
            replaceMessageId = messageId
        )

        val result = fillTimeUseCase(
            templateId = draft.templateId,
            comment = draft.comment
        )
        return if (result) {
            TimeFilledState(draft, messageId)
        } else {
            FillTimeErrorState(draft, messageId)
        }
    }
}
