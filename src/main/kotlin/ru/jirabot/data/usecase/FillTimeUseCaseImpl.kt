package ru.jirabot.data.usecase

import ru.jirabot.domain.usecase.FillTimeUseCase

class FillTimeUseCaseImpl: FillTimeUseCase {

    override fun invoke(templateId: Long?, comment: String?): Boolean {
        Thread.sleep(1000)
        return false
    }
}