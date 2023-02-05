package ru.jirabot.main.repository.sqlite.tables

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object UserTable : LongIdTable() {
    val botId: Column<Long> = long("bot_id")
    val auth: Column<String> = varchar("auth", 32)
    val lastState: Column<String> = varchar("last_state", 200)
}

class UserDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserDao>(UserTable)

    var botId by UserTable.botId
    var auth by UserTable.auth
    var lastState by UserTable.lastState
}
