package ru.jirabot.domain.bot

interface Client<User> {

    fun sendMessage(
        user: User,
        text: String
    )

}
