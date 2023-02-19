package ru.jirabot.domain.usecase

import ru.jirabot.domain.model.Statistic
import ru.jirabot.domain.model.User

interface GetStatisticUseCase {

    operator fun invoke(user: User): Statistic
}