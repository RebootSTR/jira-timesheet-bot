package ru.jirabot.ui

import ru.jirabot.domain.bot.Button

enum class Payloads(val text: String) {
    TEMPLATES("Мои шаблоны"),
    TIME_SHEET("Списать время"),
    SUBMIT("-✅ Submit"),
    SETTINGS("-Настройки"),
    INFO("-Информация о боте"),
    ADD("➕ Добавить"),
    BACK("\uD83D\uDD19 Назад"),
    CANCEL("Отмена"),
    ALL_WEEK_TYPE("-Всю неделю"),
    ONE_DAY_TYPE("Один день"),
    REPEAT("Повторить"),
    ;

    operator fun invoke() = Button(this.text, this.name)

    companion object {
        fun String.toPayload() = values().firstOrNull {
            it.name == this
        }
    }
}