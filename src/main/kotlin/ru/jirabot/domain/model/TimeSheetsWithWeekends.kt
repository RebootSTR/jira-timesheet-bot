package ru.jirabot.domain.model

data class TimeSheetsWithWeekends(
    val weekends: List<Boolean>,
    val timeSheets: List<TimeSheet>
)