package ru.jirabot.data.services.jira.dto

import com.google.gson.annotations.SerializedName

data class WorklogResultDto(
    @SerializedName("worklogs")
    val worklogs: List<WorklogDto>
)