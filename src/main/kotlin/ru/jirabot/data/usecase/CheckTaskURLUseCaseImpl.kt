package ru.jirabot.data.usecase

import com.atlassian.jira.rest.client.api.JiraRestClient
import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import ru.jirabot.di.DI
import ru.jirabot.domain.JiraAuthenticationHandler
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.Settings
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import java.net.URI

class CheckTaskURLUseCaseImpl : CheckTaskURLUseCase {

    private val userRepository: UserRepository = DI()
    private val settingsRepository: SettingsRepository = DI()

    override fun invoke(user: User, url: String?): Boolean {
        url ?: return false

        val auth = userRepository.getUserAuth(user)
        val authHandler = JiraAuthenticationHandler(auth)
        val uri = URI.create(settingsRepository.getSettingsValue(Settings.JIRA_HOST))
        val client = AsynchronousJiraRestClientFactory()
            .createWithAuthenticationHandler(uri, authHandler)

        try {
            client.findIssue(url)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun JiraRestClient.findIssue(issueUrl: String): Issue? =
        searchClient
            .searchJql(FIND_ISSUE_JQL.format(getIssueKey(issueUrl)))
            .claim()
            .issues
            .first()


    /**
     * Вычленяет ключ задачи из url к задаче
     */
    private fun getIssueKey(url: String): String {
        return url
            .split("?")[0]
            .split("/")
            .last()
    }

    companion object {
        const val FIND_ISSUE_JQL = "key = %s"
    }
}