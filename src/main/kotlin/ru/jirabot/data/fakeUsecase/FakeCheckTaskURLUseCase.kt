package ru.jirabot.data.fakeUsecase

import ru.jirabot.domain.usecase.CheckTaskURLUseCase

class FakeCheckTaskURLUseCase : CheckTaskURLUseCase {

    override fun invoke(url: String): Boolean {
        return url.contains("https://")
    }
}