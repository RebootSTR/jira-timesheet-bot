package ru.jirabot.main.database.datasource

import org.jetbrains.exposed.sql.Database

abstract class MyDataSource {

    /**
     * Функция для открытия соединения с базой
     * Должна вызываться перед использованием БД
     */
    fun create() {
        init(connect())
    }

    /**
     * Функция выполняется после создания базы
     */
    protected abstract fun init(db: Database)

    /**
     * Функция для соединения с базой и возврата сущности DataBase
     */
    protected abstract fun connect(): Database
}