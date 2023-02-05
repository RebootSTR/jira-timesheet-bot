package ru.jirabot.domain.bot

interface Client<User> {

    fun sendMessage(
        user: User,
        text: String,
        buttons: List<List<Button>>? = null
    )

    fun replaceMessage(
        user: User,
        messageId: Long, // если уж юзер через дженерик, то сообщение тоже надо но мне лень
        text: String,
        buttons: List<List<Button>>? = null
    )
}
