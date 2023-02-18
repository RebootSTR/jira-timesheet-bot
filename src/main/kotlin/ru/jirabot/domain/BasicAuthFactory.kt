package ru.jirabot.domain

import java.util.*

object BasicAuthFactory {

    private const val BASIC_HEADER = "Basic "

    fun create(login: String, password: CharArray): CharArray {
        val credentials: ByteArray = ("$login:${String(password)}").toByteArray()
        return (BASIC_HEADER + String(Base64.getEncoder().encode(credentials))).toCharArray()
    }
}