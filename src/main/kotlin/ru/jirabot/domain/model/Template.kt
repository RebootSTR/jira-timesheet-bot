package ru.jirabot.domain.model

data class Template(
    val id: Long,
    val user: User,
    val url: String,
    val title: String,
    val startTimeInMinutes: Int,
    val hours: Int,
)
