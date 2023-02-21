package ru.jirabot.ui.states.logic2.template.create

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.serialization.Exclude
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class CheckURLState(
    private val template: TemplateDraft,
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {

    @Exclude
    private val checkTaskURLUseCase: CheckTaskURLUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        return if (checkTaskURLUseCase(user, template.url)) {
            TaskHoursInputState(template)
        } else {
            WrongURLState(template)
        }
    }
}
