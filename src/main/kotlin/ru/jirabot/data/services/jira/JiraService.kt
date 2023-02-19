package ru.jirabot.data.services.jira

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import ru.jirabot.data.services.com.deniz.jira.worklog.dto.IssueDto
import ru.jirabot.data.services.jira.dto.UserDto
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

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}