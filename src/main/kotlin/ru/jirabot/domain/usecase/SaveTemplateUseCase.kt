package ru.jirabot.domain.usecase

import ru.jirabot.domain.entities.Template

interface SaveTemplateUseCase {

    operator fun invoke(template: Template)
}