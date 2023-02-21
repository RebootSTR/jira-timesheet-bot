package ru.jirabot.data.repository

import ru.jirabot.data.repository.cache.Cache
import ru.jirabot.data.repository.cache.Cached
import ru.jirabot.data.repository.cache.InvalidateCache
import ru.jirabot.data.services.com.deniz.jira.worklog.CalendarService
import ru.jirabot.data.services.jira.JiraService
import ru.jirabot.data.services.jira.dto.WorkLogRequestDto
import ru.jirabot.di.DI
import ru.jirabot.domain.model.JiraUser
import ru.jirabot.domain.model.TimeSheet
import ru.jirabot.domain.model.TimeSheetsWithWeekends
import ru.jirabot.domain.repository.JiraRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class JiraRepositoryImpl : JiraRepository {

    private val jiraService: JiraService = DI()
    private val calendarService: CalendarService = DI()

    override fun authIsCorrect(auth: CharArray): Boolean {
        return jiraService.myself(String(auth)).execute().isSuccessful
    }

    // todo больно и сложно но надо как-то проверять полученные данные, чтобы не ловить эксепшены
    @Cached
    override fun getUser(auth: CharArray): JiraUser = Cache.cached(
        listOf(JiraRepository::getUser, String(auth))
    ) {
        val user = jiraService.myself(String(auth)).execute().body()!!
        return@cached JiraUser(
            login = user.login,
            name = user.name
        )
    }

    // todo больно и сложно но надо как-то проверять полученные данные, чтобы не ловить эксепшены
    /**
     * Возвращает список таймшитов для пользователя.
     * Результат кешируется, нужно инвалидировать при создании нового таймшита
     */
    @Cached
    override fun getTimeSheetsByDate(
        auth: CharArray,
        login: String,
        startDay: LocalDate,
        endDay: LocalDate
    ): TimeSheetsWithWeekends = Cache.cached(
        listOf(JiraRepository::getTimeSheetsByDate, String(auth))
    ) {
        val response = calendarService.getDateTimesheets(
            auth = String(auth),
            login = login,
            startDate = startDay.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            endDate = endDay.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        ).execute().body()!!
        val weekends = response.isWeekend
        val timeSheets = response.projects.flatMap {
            it.issues.flatMap {
                it.worklogs.map {
                    TimeSheet(
                        spentTimeSeconds = it.timeSpentSeconds,
                        day = LocalDateTime.ofEpochSecond(it.startTimeStamp / 1000, 0, ZoneOffset.UTC).toLocalDate()
                    )
                }
            }
        }
        return@cached TimeSheetsWithWeekends(
            weekends = weekends,
            timeSheets = timeSheets
        )
    }

    @InvalidateCache
    override fun fillTime(
        auth: CharArray,
        taskName: String,
        startTime: ZonedDateTime,
        spentTimeSeconds: Int,
        comment: String
    ): Boolean = Cache.invalidateAfter(
        listOf(JiraRepository::getTimeSheetsByDate, String(auth))
    ) {
        val formatter = DateTimeFormatter.ofPattern(DATE_REQUEST_FORMAT)
        val response = jiraService.worklog(
            auth = String(auth),
            issueName = taskName,
            worklog = WorkLogRequestDto(
                comment = comment,
                startedDateTime = startTime.format(formatter),
                timeSpentSeconds = spentTimeSeconds,
            )
        ).execute()

        return@invalidateAfter response.isSuccessful
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val DATE_REQUEST_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    }
}