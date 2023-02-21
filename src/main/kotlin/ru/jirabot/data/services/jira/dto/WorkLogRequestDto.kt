package ru.jirabot.data.services.jira.dto

import com.google.gson.annotations.SerializedName

/**
 * @param comment Комментарий к таймшиту
 * @param startedDateTime время начала работы в формате 2023-02-21T15:12:00.000+0000
 * @param timeSpentSeconds потраченное время в секундах
 */
data class WorkLogRequestDto(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("started")
    val startedDateTime: String,
    @SerializedName("timeSpentSeconds")
    val timeSpentSeconds: Int,
)