package ru.jirabot.main

import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase

object Tests {

    /**
     * Если не упало, значит работает))))
     */
    fun testDi() {
        configureFakeDi()

        DI.get<Dictionary>()
        DI.get<AuthUserUseCase>()
        DI.get<CheckTaskURLUseCase>()
    }
}