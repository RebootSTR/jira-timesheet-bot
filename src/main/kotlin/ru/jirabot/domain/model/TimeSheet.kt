package ru.jirabot.domain.model

import java.time.LocalDate

data class TimeSheet(
    val spentTimeSeconds: Int,
    val day: LocalDate
)