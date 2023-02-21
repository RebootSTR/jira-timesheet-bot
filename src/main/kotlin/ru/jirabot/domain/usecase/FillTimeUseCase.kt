package ru.jirabot.domain.usecase

import ru.jirabot.domain.model.User
import java.time.LocalDate

interface FillTimeUseCase {

    operator fun invoke(user: User, templateId: Long?, comment: String?, day: LocalDate): Boolean
}