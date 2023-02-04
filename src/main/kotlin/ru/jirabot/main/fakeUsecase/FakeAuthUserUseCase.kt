package ru.jirabot.main.fakeUsecase

import ru.jirabot.domain.usecase.AuthUserUseCase

class FakeAuthUserUseCase: AuthUserUseCase {
    override fun invoke(login: String, password: String): Boolean {
        return login == "login" && password == "password"
    }
}