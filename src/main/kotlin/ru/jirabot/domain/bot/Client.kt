package ru.jirabot.domain.bot

import ru.jirabot.domain.model.User

interface Client {

    fun sendMessage(
        user: User,
        text: String,
        replaceMessageId: Long? = null,
        buttons: List<List<Button>>? = null
    ): Long?
}
