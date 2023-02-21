package ru.jirabot.data.usecase

import ru.jirabot.data.services.jira.JiraService
import ru.jirabot.data.utils.TemplateUtils.getIssueKey
import ru.jirabot.di.DI
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.CheckTaskURLUseCase

class CheckTaskURLUseCaseImpl : CheckTaskURLUseCase {

    private val userRepository: UserRepository = DI()
    private val jiraService: JiraService = DI()

    override fun invoke(user: User, url: String?): Boolean {
        url ?: return false

        val auth = userRepository.getUserAuth(user)

        val response = jiraService.issue(String(auth), url.getIssueKey()).execute()

        return response.isSuccessful
    }
}