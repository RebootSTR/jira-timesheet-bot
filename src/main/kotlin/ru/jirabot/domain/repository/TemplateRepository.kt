package ru.jirabot.domain.repository

import ru.jirabot.domain.entities.Template
import ru.jirabot.domain.entities.User

interface TemplateRepository {

    fun saveTemplate(template: Template)

    fun getTemplates(user: User): List<Template>
}