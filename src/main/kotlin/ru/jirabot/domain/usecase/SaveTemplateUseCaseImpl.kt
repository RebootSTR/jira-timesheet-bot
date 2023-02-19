package ru.jirabot.domain.usecase

import ru.jirabot.di.DI
import ru.jirabot.domain.model.Template
import ru.jirabot.domain.repository.TemplateRepository

class SaveTemplateUseCaseImpl: SaveTemplateUseCase {

    private val templateRepository: TemplateRepository = DI()

    override fun invoke(template: Template) {
        templateRepository.saveTemplate(template)
    }
}