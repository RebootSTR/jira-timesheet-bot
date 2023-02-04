package ru.jirabot.domain.usecase

/**
 * Авторизация и проверка правильности логин:пароль
 */
interface AuthUserUseCase {

    operator fun invoke(login: String, password: String): Boolean
}