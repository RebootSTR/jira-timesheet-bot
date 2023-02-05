package ru.jirabot.main.usecase

import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import ru.jirabot.di.DI
import ru.jirabot.domain.JiraAuthenticationHandler
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.entities.User
import java.net.URI

class AuthUserUseCaseImpl : AuthUserUseCase<User> {

    private val settingsRepository: SettingsRepository = DI()
    private val userRepository: UserRepository<User> = DI()

    override fun invoke(user: User, login: String, password: CharArray): Boolean {
        val authHandler = JiraAuthenticationHandler.create(login, password)
        val uri = URI.create(settingsRepository.getJiraHost())

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
