package ru.jirabot.domain.repository

import ru.jirabot.domain.entities.Template

interface TemplateRepository<User> {

    fun saveTemplate(user: User, template: Template)

    fun getTemplates(user: User)
}