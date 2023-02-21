package ru.jirabot.ui.states.logic2.filltime

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.FillTimeUseCase
import ru.jirabot.ui.drafts.FillTimeDraft
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState
import java.time.LocalDate

class TryFillTimeState(
    private val draft: FillTimeDraft,
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {

    @Exclude
    private val fillTimeUseCase: FillTimeUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        sendMessage(
            user = user,
            text = dictionary["TryFillTimeState"],
        )

        val result = fillTimeUseCase(
            user = user,
            templateId = draft.templateId,
            comment = draft.comment,
            // todo возможность делать таймшиты не только сегодня
            day = LocalDate.now()
        )
        return if (result) {
            TimeFilledState(draft, messageId)
        } else {
            FillTimeErrorState(draft, messageId)
        }
    }
}
