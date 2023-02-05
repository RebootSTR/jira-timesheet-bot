package ru.jirabot.main

import com.atlassian.jira.rest.client.api.JiraRestClient
import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.api.domain.input.WorklogInput
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory
import org.joda.time.DateTime
import ru.jirabot.di.DI
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.telegram.TelegramBot
import ru.jirabot.terminal.TerminalBot
import ru.jirabot.terminal.configureTerminal
import java.net.URI

const val FIND_ISSUE_JQL = "key = %s"
fun main() {
    runTelegram()
//    runTerminal()
//    Tests.testDi()
}

fun runTelegram() {
    configureDi()
    TelegramBot().run()
}

fun runTerminal() {
    configureTerminal()
    TerminalBot.run()
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
