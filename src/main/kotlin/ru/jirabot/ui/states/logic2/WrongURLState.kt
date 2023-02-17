package ru.jirabot.ui.states.logic2

import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.entities.User
import ru.jirabot.ui.drafts.TemplateDraft

class WrongURLState(
    private val template: TemplateDraft,
) : RedirectBotState() {

    override fun interactWithUser(user: User): BotState {
        client.sendMessage(
            user = user,
            text = dictionary["WrongURLState"]
        )
        return TaskURLInputState(template.apply { url = null }, silent = true)
    }

}
