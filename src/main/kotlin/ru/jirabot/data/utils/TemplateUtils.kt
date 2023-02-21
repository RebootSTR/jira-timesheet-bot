package ru.jirabot.data.utils

object TemplateUtils {

    /**
     * Вычленяет ключ задачи из url к задаче
     */
    fun String.getIssueKey(): String {
        return this
            .split("?")[0]
            .split("/")
            .last()
    }
}