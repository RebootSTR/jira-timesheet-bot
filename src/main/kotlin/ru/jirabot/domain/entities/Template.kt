package ru.jirabot.domain.entities

import org.joda.time.DateTime

data class Template(
    val id: Long,
    val owner: User,
    val info: TemplateInfo,
)

data class TemplateInfo(
    val url: String,
    val taskName: String,
    val startDate: DateTime,
    val hours: Int,
)
