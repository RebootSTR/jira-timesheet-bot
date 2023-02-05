package ru.jirabot.domain.bot

/**
 * @param title Текст на кнопке
 * @param payload Поле для определения "смысла" кнопки
 */
data class Button(
    val title: String,
    val payload: String,
)