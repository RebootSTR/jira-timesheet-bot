package ru.jirabot.main

import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.domain.entities.User

object Tests {

    /**
     * Если не упало, значит работает))))
     */
    fun testDi() {
        configureDi()

        DI.get<Dictionary>()
        DI.get<AuthUserUseCase<*>>()
        DI.get<CheckTaskURLUseCase>()
        DI.get<UserRepository<User>>()
        DI.get<SettingsRepository>()
    }
}
