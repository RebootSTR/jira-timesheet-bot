package ru.jirabot.data.usecase

import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import ru.jirabot.di.DI
import ru.jirabot.domain.JiraAuthenticationHandler
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.ui.common.User
import ru.jirabot.domain.repository.Settings
import java.net.URI

class AuthUserUseCaseImpl : AuthUserUseCase<User> {

    private val settingsRepository: SettingsRepository = DI()
    private val userRepository: UserRepository<User> = DI()

    override fun invoke(user: User, login: String, password: CharArray): Boolean {
        val authHandler = JiraAuthenticationHandler.create(login, password)
        val uri = URI.create(settingsRepository.getSettingsValue(Settings.JIRA_HOST))

        val client = AsynchronousJiraRestClientFactory()
            .createWithAuthenticationHandler(uri, authHandler)

        try {
            client.userClient.getUser(login).claim()
        } catch (e: Exception) {
            return false
        }

        userRepository.saveUserAuth(user, authHandler.basic)
        return true
    }
}
