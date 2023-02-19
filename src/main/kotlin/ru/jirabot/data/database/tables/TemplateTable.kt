package ru.jirabot.data.database.tables

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import ru.jirabot.domain.model.Template
import ru.jirabot.domain.model.User

object TemplateTable : LongIdTable() {
    val user: Column<Long> = long("user_id")
    val title: Column<String> = text("title")
    val url: Column<String> = text("url")
    val hours: Column<Int> = integer("hours")
    val startTime: Column<Int> = integer("start_time")
}

class TemplateDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TemplateDao>(TemplateTable)

    var user by TemplateTable.user
    var title by TemplateTable.title
    var url by TemplateTable.url
    var hours by TemplateTable.hours
    var startTime by TemplateTable.startTime

    fun toModel() = Template(
        id = id.value,
        user = User(user),
        url = url,
        title = title,
        startTimeInMinutes = startTime,
        hours = hours
    )
}
