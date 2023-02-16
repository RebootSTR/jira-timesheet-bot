package ru.jirabot.domain.bot

import ru.jirabot.domain.entities.User

interface Client {

    fun sendMessage(
        user: User,
        text: String,
        buttons: List<List<Button>>? = null
    )

    fun replaceMessage(
        user: User,
        messageId: Long,
        text: String,
        buttons: List<List<Button>>? = null
    )
}
