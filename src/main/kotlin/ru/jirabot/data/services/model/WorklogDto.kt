package ru.jirabot.data.services.model

import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

/**
 * Единичный таймшит
 *
 * @param author Автор таймшита
 * @param comment Комментарий таймшита
 * @param started Время начала таймшита
 * @param timeSpentSeconds Затраченое время таймшита в секундах
 */
data class WorklogDto(
    @SerializedName("author")
    val author: AuthorDto,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("started")
    val started: DateTime,
    @SerializedName("timeSpentSeconds")
    val timeSpentSeconds: Int,
)

/**
 * Автор таймшита
 *
 * @param login логин юзера в формате "имя.фамилия"
 */
data class AuthorDto(
    @SerializedName("name")
    val login: String
)