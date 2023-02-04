package ru.jirabot.domain.dictionary

interface Dictionary {

    operator fun get(key: String): String
}