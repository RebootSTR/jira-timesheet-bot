package ru.jirabot.domain

import com.atlassian.httpclient.api.Request
import com.atlassian.jira.rest.client.api.AuthenticationHandler
import org.apache.commons.codec.binary.Base64

class JiraAuthenticationHandler(
    val basic: CharArray
): AuthenticationHandler {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BASIC_HEADER = "Basic "

        fun create(login: String, password: CharArray) =
            JiraAuthenticationHandler(encodeToBasic(login, password))

        private fun encodeToBasic(login: String, password: CharArray): CharArray {
            val credentials: ByteArray = ("$login:${String(password)}").toByteArray()
            return String(Base64.encodeBase64(credentials)).toCharArray()
        }
    }

    override fun configure(builder: Request.Builder) {
        builder.setHeader(AUTHORIZATION_HEADER, BASIC_HEADER + String(basic))
    }
}