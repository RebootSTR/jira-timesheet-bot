package ru.jirabot.domain.entities

data class Template(
    val id: Long,
    val user: User,
    val url: String,
    val title: String,
    val startTimeInMinutes: Int,
    val hours: Int,
)
