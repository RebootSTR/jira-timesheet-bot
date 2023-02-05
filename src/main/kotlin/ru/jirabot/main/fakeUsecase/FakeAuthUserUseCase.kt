package ru.jirabot.main.fakeUsecase

import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.telegram.TelegramUser

class FakeAuthUserUseCase: AuthUserUseCase<TelegramUser> {

    override fun invoke(user: TelegramUser, login: String, password: CharArray): Boolean {
        return login == "login" && password.contentEquals("password".toCharArray())
    }
}