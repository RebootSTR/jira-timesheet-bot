package ru.jirabot.domain.usecase

import ru.jirabot.domain.model.Template

interface SaveTemplateUseCase {

    operator fun invoke(template: Template)
}