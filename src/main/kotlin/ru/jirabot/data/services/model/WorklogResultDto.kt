package ru.jirabot.data.services.model

import com.google.gson.annotations.SerializedName

data class WorklogResultDto(
    @SerializedName("worklogs")
    val worklogs: List<WorklogDto>
)