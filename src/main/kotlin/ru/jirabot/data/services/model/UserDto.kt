package ru.jirabot.data.services.model

import com.google.gson.annotations.SerializedName

/**
 * Пользователь Jira
 *
 * @param login Логин пользователя в формате "имя.фамилия"
 * @param name Имя пользователя
 */
data class UserDto(

    @SerializedName("name")
    val login: String,

    @SerializedName("displayName")
    val name: String
)