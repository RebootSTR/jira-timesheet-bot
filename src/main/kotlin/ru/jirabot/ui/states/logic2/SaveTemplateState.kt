package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.Template
import ru.jirabot.domain.model.User
import ru.jirabot.domain.usecase.SaveTemplateUseCase
import ru.jirabot.ui.drafts.TemplateDraft

class SaveTemplateState(
    private val template: TemplateDraft,
) : RedirectBotState() {

    private val saveTemplateUseCase: SaveTemplateUseCase = DI()

    override fun interactWithUser(user: User): BotState {
        saveTemplateUseCase(
            Template(
                id = 0L,
                user = user,
                url = template.url!!,
                title = template.title!!,
                startTimeInMinutes = template.startTimeInMinutes!!,
                hours = template.hours!!
            )
        )
        return TemplateMenuState()
    }
}
