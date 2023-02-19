package ru.jirabot.data.usecase

import ru.jirabot.di.DI
import ru.jirabot.domain.BasicAuthFactory
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.JiraRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase

class AuthUserUseCaseImpl : AuthUserUseCase {

    private val userRepository: UserRepository = DI()
    private val jiraRepository: JiraRepository = DI()

    override fun invoke(user: User, login: String, password: CharArray): Boolean {
        val auth = BasicAuthFactory.create(login, password)

        if (jiraRepository.authIsCorrect(auth).not()) {
            return false
        }

        userRepository.saveUserAuth(user, auth)
        return true
    }
}
