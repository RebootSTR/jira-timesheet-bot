package ru.jirabot.domain.usecase

import ru.jirabot.domain.entities.User

/**
 * Авторизация и проверка правильности логин:пароль
 */
interface AuthUserUseCase {

    operator fun invoke(user: User, login: String, password: CharArray): Boolean
}