package ru.jirabot.domain

sealed class UserAction {
    class Message(val text: String): UserAction()
    class ButtonClick(val payload: Any): UserAction()
}