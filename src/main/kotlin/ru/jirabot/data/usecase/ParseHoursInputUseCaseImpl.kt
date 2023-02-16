package ru.jirabot.data.usecase

import ru.jirabot.domain.usecase.ParseHoursInputUseCase

class ParseHoursInputUseCaseImpl : ParseHoursInputUseCase {

    override fun invoke(input: String?): Int? {
        return try {
            val hours = input!!.toInt()
            if (hours in 1..10) {
                hours
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}