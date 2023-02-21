package ru.jirabot.data.services.jira

import retrofit2.Call
import retrofit2.http.*
import ru.jirabot.data.services.com.deniz.jira.worklog.dto.IssueDto
import ru.jirabot.data.services.jira.dto.UserDto
import ru.jirabot.data.services.jira.dto.WorkLogRequestDto
import ru.jirabot.data.services.jira.dto.WorklogResultDto

interface JiraService {

    @GET("/rest/api/2/myself")
    fun myself(
        @Header(AUTHORIZATION) auth: String
    ): Call<UserDto>

    @GET("/rest/api/2/issue/{issueName}")
    fun issue(
        @Header(AUTHORIZATION) auth: String,
        @Path("issueName") issueName: String
    ): Call<IssueDto>

    @GET("/rest/api/2/issue/{issueName}/worklog")
    fun worklogs(
        @Header(AUTHORIZATION) auth: String,
        @Path("issueName") issueName: String
    ): Call<WorklogResultDto>

    @Headers(CONTENT_TYPE_JSON)
    @POST("/rest/api/2/issue/{issueName}/worklog")
    fun worklog(
        @Header(AUTHORIZATION) auth: String,
        @Path("issueName") issueName: String,
        @Body worklog: WorkLogRequestDto
    ): Call<WorklogResultDto>

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val CONTENT_TYPE_JSON = "Content-Type: application/json"
    }
}