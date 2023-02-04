package ru.jirabot.domain.usecase

interface CheckTaskURLUseCase {

    operator fun invoke(url: String): Boolean
}