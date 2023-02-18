package ru.jirabot.data.usecase

import ru.jirabot.data.services.JiraService
import ru.jirabot.di.DI
import ru.jirabot.domain.BasicAuthFactory
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase

class AuthUserUseCaseImpl : AuthUserUseCase {

    private val userRepository: UserRepository = DI()
    private val jiraService: JiraService = DI()

    override fun invoke(user: User, login: String, password: CharArray): Boolean {
        val auth = BasicAuthFactory.create(login, password)

        val response = jiraService.myself(String(auth)).execute()

        if (response.isSuccessful.not()) {
            return false
        }

        userRepository.saveUserAuth(user, auth)
        return true
    }
}
