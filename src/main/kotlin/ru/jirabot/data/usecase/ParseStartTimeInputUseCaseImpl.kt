package ru.jirabot.data.usecase

import ru.jirabot.domain.usecase.ParseStartTimeInputUseCase

class ParseStartTimeInputUseCaseImpl : ParseStartTimeInputUseCase {

    override fun invoke(input: String?): Int? {
        return try {
            val parts = input!!.split(":")
            val hours = parts[0].toInt()
            val minutes = parts[1].toInt()

            if (hours in 8..20 && minutes in 0..60) {
                hours * 60 + minutes
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}