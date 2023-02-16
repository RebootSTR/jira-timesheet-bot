package ru.jirabot.domain.usecase

interface ParseHoursInputUseCase {

    operator fun invoke(input: String?): Int?
}