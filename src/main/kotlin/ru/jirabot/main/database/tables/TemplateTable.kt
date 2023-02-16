package ru.jirabot.main.database.tables

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object TemplateTable : LongIdTable() {
    val user: Column<Long> = long("user_id")
    val name: Column<String> = text("name")
    val url: Column<String> = text("url")
    val hours: Column<Int> = integer("hours")
    val startTime: Column<Int> = integer("start_time")
}

class TemplateDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TemplateDao>(TemplateTable)

    var user by TemplateTable.user
    var name by TemplateTable.user
    var url by TemplateTable.url
    var hours by TemplateTable.hours
    var startTime by TemplateTable.startTime
}
