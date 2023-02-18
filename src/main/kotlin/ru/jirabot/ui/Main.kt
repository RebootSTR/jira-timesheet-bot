package ru.jirabot.ui

import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.services.JiraService
import ru.jirabot.di.DI
import ru.jirabot.ui.telegram.TelegramBot

fun main() {
    runTelegram()
//    retrofitTest("", "")
    // Tests.testDi()
//    Tests.testSqliteDb()
}

fun runTelegram() {
    configureDi()

    // открытие БД
    DI<MyDataSource>().create()
    // запуск бота
    TelegramBot().run()
}

fun retrofitTest(auth: String, taskName: String) {
    configureDi()
    val service: JiraService = DI()
    val user = service.myself(auth).execute().body()!!
    println("Имя пользователя: ${user.name}")
    val task = service.issue(auth, taskName).execute()
    if (task.isSuccessful) {
        println("Задача $taskName существует!")
    } else {
        println("Задача $taskName не существует!")
        return
    }
    val worklogs = service.worklogs(auth, taskName).execute().body()!!
    println("Таймшиты пользователя:")
    worklogs.worklogs
        .filter { it.author.login == user.login }
        .forEach { println(it) }
}