package ru.jirabot.domain.usecase

import ru.jirabot.domain.model.User

interface CheckTaskURLUseCase {

    operator fun invoke(user: User, url: String?): Boolean
}