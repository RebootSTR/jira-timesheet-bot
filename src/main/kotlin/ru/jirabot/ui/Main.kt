package ru.jirabot.ui

import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.services.jira.JiraService
import ru.jirabot.di.DI
import ru.jirabot.domain.model.User
import ru.jirabot.domain.usecase.GetStatisticUseCase
import ru.jirabot.ui.telegram.TelegramBot

fun main() {
//    runTelegram()
//    retrofitTest("", "")
//    statisticTest(0L)
    // Tests.testDi()
//    Tests.testSqliteDb()
    Tests.testCache()
}

fun runTelegram() {
    configureDi()

    // открытие БД
    DI<MyDataSource>().create()
    // запуск бота
    TelegramBot().run()
}

fun statisticTest(userId: Long) {
    configureDi()
    // открытие БД
    DI<MyDataSource>().create()

    val user = User(userId = userId)
    val usecase: GetStatisticUseCase = DI()
    val statistic = usecase.invoke(user)
    println(statistic)
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