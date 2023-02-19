package ru.jirabot.data.repository.sqlite

import org.jetbrains.exposed.sql.transactions.transaction
import ru.jirabot.data.database.tables.TemplateDao
import ru.jirabot.data.database.tables.TemplateTable
import ru.jirabot.domain.model.Template
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.TemplateRepository

class SqliteTemplateRepository : TemplateRepository {

    override fun saveTemplate(template: Template) =
        transaction {
            TemplateDao.new {
                user = template.user.userId
                title = template.title
                url = template.url
                hours = template.hours
                startTime = template.startTimeInMinutes
            }
            commit()
        }

    override fun getTemplates(user: User): List<Template> =
        transaction {
            TemplateDao.find {
                TemplateTable.user eq user.userId
            }.map { it.toModel() }
        }
}
