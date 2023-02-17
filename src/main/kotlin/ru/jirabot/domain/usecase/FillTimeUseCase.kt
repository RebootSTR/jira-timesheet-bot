package ru.jirabot.domain.usecase

interface FillTimeUseCase {

    operator fun invoke(templateId: Long?, comment: String?): Boolean
}