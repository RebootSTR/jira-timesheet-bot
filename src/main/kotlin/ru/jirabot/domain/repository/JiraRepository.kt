package ru.jirabot.domain.repository

import ru.jirabot.domain.model.JiraUser
import ru.jirabot.domain.model.TimeSheetsWithWeekends
import java.time.LocalDate
import java.time.ZonedDateTime

interface JiraRepository {

    fun authIsCorrect(auth: CharArray): Boolean

    fun getUser(auth: CharArray): JiraUser

    fun getTimeSheetsByDate(
        auth: CharArray,
        login: String,
        startDay: LocalDate,
        endDay: LocalDate
    ): TimeSheetsWithWeekends

    fun fillTime(
        auth: CharArray,
        taskName: String,
        startTime: ZonedDateTime,
        spentTimeSeconds: Int,
        comment: String
    ): Boolean
}