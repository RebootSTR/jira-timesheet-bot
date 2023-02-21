package ru.jirabot.ui

import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.repository.JiraRepositoryImpl
import ru.jirabot.data.services.jira.JiraService
import ru.jirabot.di.DI
import ru.jirabot.domain.model.User
import ru.jirabot.domain.usecase.GetStatisticUseCase
import ru.jirabot.ui.telegram.TelegramBot
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun main() {
    runTelegram()
//    timeTests()
//    retrofitTest("", "")
//    statisticTest(0L)
    // Tests.testDi()
//    Tests.testSqliteDb()
//    Tests.testCache()
}

fun timeTests() {
    val time = LocalDateTime.of(2023,2,20,13,0)
    val zonedTime = time.atZone(ZoneId.of("+4"))
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    println(zonedTime.format(formatter))
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