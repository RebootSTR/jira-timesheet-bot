package ru.jirabot.domain.repository

import ru.jirabot.domain.model.Template
import ru.jirabot.domain.model.User

interface TemplateRepository {

    fun saveTemplate(template: Template)

    fun getTemplates(user: User): List<Template>
}