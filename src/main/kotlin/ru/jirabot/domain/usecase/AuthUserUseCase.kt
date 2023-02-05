package ru.jirabot.domain.usecase

/**
 * Авторизация и проверка правильности логин:пароль
 */
interface AuthUserUseCase<User> {

    operator fun invoke(user: User, login: String, password: CharArray): Boolean
}