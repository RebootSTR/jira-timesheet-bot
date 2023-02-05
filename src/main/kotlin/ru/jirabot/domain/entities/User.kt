package ru.jirabot.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val botId: Long
)
