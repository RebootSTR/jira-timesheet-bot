package ru.jirabot.data.services.com.deniz.jira.worklog

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.jirabot.data.services.com.deniz.jira.worklog.dto.DateTimesheetsDto

interface CalendarService {

    /**
     * @param startDate дата в формате "yyyy-mm-dd"
     * @param endDate дата в формате "yyyy-mm-dd"
     */
    @GET("/rest/com.deniz.jira.worklog/1.0/timesheet/user")
    fun getDateTimesheets(
        @Header("Authorization") auth: String,
        @Query("targetKey") login: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
    ): Call<DateTimesheetsDto>
}