package ru.jirabot.domain.usecase

interface ParseStartTimeInputUseCase {

    operator fun invoke(input: String?): Int?
}