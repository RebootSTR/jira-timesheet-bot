package ru.jirabot.data.services.jira.dto

import com.google.gson.annotations.SerializedName

data class IssueDto(
    @SerializedName("key")
    val name: String
)