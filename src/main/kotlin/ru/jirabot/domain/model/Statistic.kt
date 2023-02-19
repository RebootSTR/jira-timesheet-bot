package ru.jirabot.domain.model

/**
 * @param name
 * @param templateCount
 * @param weekVisual
 */
data class Statistic(
    val name: String,
    val templateCount: Int,
    val weekVisual: String,
)
