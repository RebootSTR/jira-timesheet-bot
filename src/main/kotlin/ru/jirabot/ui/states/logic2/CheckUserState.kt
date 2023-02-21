package ru.jirabot.ui.states.logic2

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.bot.RedirectBotState
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.ui.states.logic2.common.CommonRedirectBotState

class CheckUserState(
    messageId: Long? = null
) : CommonRedirectBotState(messageId) {

    private val userRepository: UserRepository = DI()

    override fun interactWithUser(user: User): BotState {
        return if (userRepository.isUserExist(user)) {
            MenuState()
        } else {
            UsernameInputState()
        }
    }
}
