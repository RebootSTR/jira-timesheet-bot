package ru.jirabot.ui.states.logic2.template.create

import ru.jirabot.data.utils.TemplateUtils.getIssueKey
import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.model.Template
import ru.jirabot.domain.model.User
import ru.jirabot.domain.usecase.SaveTemplateUseCase
import ru.jirabot.ui.drafts.TemplateDraft
import ru.jirabot.ui.states.logic2.TemplateMenuState
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class SaveTemplateState(
    private val template: TemplateDraft,
    messageId: Long? = null,
) : CommonRedirectBotState(messageId) {

    private val saveTemplateUseCase: SaveTemplateUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        saveTemplateUseCase(
            Template(
                id = 0L,
                user = user,
                url = template.url!!,
                taskName = template.url!!.getIssueKey(),
                title = template.title!!,
                startTimeInMinutes = template.startTimeInMinutes!!,
                hours = template.hours!!
            )
        )
        return TemplateMenuState()
    }
}
