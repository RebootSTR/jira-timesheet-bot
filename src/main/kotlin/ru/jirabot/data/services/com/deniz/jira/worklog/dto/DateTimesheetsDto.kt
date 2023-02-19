package ru.jirabot.data.services.com.deniz.jira.worklog.dto

import com.google.gson.annotations.SerializedName

/**
 * @param projects Список проектов, на которые было затрачено время
 * @param isWeekend Список формата [false, false, ... , true] сопостовляющий выбранные дни и информацию о том,
 * выходной ли он
 */
data class DateTimesheetsDto(
    @SerializedName("projects")
    val projects: List<ProjectDto>,
    @SerializedName("isWeekend")
    val isWeekend: List<Boolean>
)

/**
 * @param id идентификатор проекта
 * @param name название проекта
 * @param issues список задач, на которые пользователь потратил время
 */
data class ProjectDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("issues")
    val issues: List<IssueDto>
)

/**
 * @param name имя задачи
 * @param worklogs список таймшитов, записанных на задачу
 */
data class IssueDto(
    @SerializedName("key")
    val name: String,
    @SerializedName("workLogs")
    val worklogs: List<WorklogDto>
)

/**
 * @param timeSpentSeconds потраченное время в секундах
 * @param comment комментарий таймшита
 */
data class WorklogDto(
    @SerializedName("timeSpent")
    val timeSpentSeconds: Int ,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("workStart")
    val startTimeStamp: Long
)