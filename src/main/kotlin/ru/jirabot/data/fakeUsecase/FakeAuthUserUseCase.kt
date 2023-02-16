package ru.jirabot.data.fakeUsecase

import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.entities.User

class FakeAuthUserUseCase: AuthUserUseCase {

    override fun invoke(user: User, login: String, password: CharArray): Boolean {
        return login == "login" && password.contentEquals("password".toCharArray())
    }
}
