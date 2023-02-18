package ru.jirabot.data.services.model

import com.google.gson.annotations.SerializedName

data class IssueDto(
    @SerializedName("key")
    val name: String
)