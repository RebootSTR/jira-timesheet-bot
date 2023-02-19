package ru.jirabot.data.repository

import ru.jirabot.data.services.com.deniz.jira.worklog.CalendarService
import ru.jirabot.data.services.jira.JiraService
import ru.jirabot.di.DI
import ru.jirabot.domain.model.JiraUser
import ru.jirabot.domain.model.TimeSheet
import ru.jirabot.domain.model.TimeSheetsWithWeekends
import ru.jirabot.domain.repository.JiraRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class JiraRepositoryImpl : JiraRepository {

    private val jiraService: JiraService = DI()
    private val calendarService: CalendarService = DI()

    override fun authIsCorrect(auth: CharArray): Boolean {
        return jiraService.myself(String(auth)).execute().isSuccessful
    }

    // todo больно и сложно но надо как-то проверять полученные данные, чтобы не ловить эксепшены
    override fun getUser(auth: CharArray): JiraUser {
        val user = jiraService.myself(String(auth)).execute().body()!!
        return JiraUser(
            login = user.login,
            name = user.name
        )
    }

    // todo больно и сложно но надо как-то проверять полученные данные, чтобы не ловить эксепшены
    override fun getTimeSheetsByDate(
        auth: CharArray,
        login: String,
        startDay: LocalDate,
        endDay: LocalDate
    ): TimeSheetsWithWeekends {
        val response = calendarService.getDateTimesheets(
            auth = String(auth),
            login = login,
            startDate = startDay.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            endDate = endDay.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        ).execute().body()!!
        val weekends = response.isWeekend
        val timeSheets = response.projects.flatMap {
            it.issues.flatMap {
                it.worklogs.mapNotNull {
                    TimeSheet(
                        spentTimeSeconds = it.timeSpentSeconds,
                        day = LocalDateTime.ofEpochSecond(it.startTimeStamp / 1000, 0, ZoneOffset.UTC).toLocalDate()
                    )
                }
            }
        }
        return TimeSheetsWithWeekends(
            weekends = weekends,
            timeSheets = timeSheets
        )
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }
}