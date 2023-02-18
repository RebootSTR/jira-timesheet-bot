package ru.jirabot.ui

import com.atlassian.jira.rest.client.api.JiraRestClient
import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.input.WorklogInput
import org.joda.time.DateTime
import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.services.JiraService
import ru.jirabot.di.DI
import ru.jirabot.ui.telegram.TelegramBot

const val FIND_ISSUE_JQL = "key = %s"
fun main() {
//    runTelegram()
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

fun runTest() {
    val issueUrl = ""
    val comment = "Commentary"
    val startTime = DateTime.now().withHourOfDay(8).withMinuteOfHour(0)
    val workHours = 8

    val client = auth() ?: return
    val issue = client.findIssue(issueUrl) ?: return
    client.setTimeSheet(
        issue = issue,
        comment = comment,
        startTime = startTime,
        workHours = workHours
    )
}

fun JiraRestClient.findIssue(issueUrl: String): Issue? =
    searchClient
        .searchJql(FIND_ISSUE_JQL.format(getIssueKey(issueUrl)))
        .claim()
        .issues
        .first()

/**
 * Вычленяет ключ задачи из url к задаче
 */
fun getIssueKey(url: String): String {
    return url
        .split("?")[0]
        .split("/")
        .last()
}

fun JiraRestClient.setTimeSheet(issue: Issue, comment: String, startTime: DateTime, workHours: Int) {
    val input = WorklogInput.create(
        issue.self,
        comment,
        startTime,
        workHours * 60
    )

    issueClient // TODO idk че дальше хехе я запутался
}

fun auth(): JiraRestClient? {
    error("Код только для ознакомления, потихоньку перезжает в нормальные слои")
}
