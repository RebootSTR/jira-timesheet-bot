package ru.jirabot.main

import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.main.configurators.configGson
import ru.jirabot.main.dictionary.DictionaryImpl
import ru.jirabot.main.fakeUsecase.FakeCheckTaskURLUseCase
import ru.jirabot.main.repository.LocalSettingsRepository
import ru.jirabot.main.repository.LocalUserRepository
import ru.jirabot.main.usecase.AuthUserUseCaseImpl

fun configureDi() {
    DI.single<Dictionary> {
        DictionaryImpl()
    }

    DI.single<UserRepository<*>> {
        LocalUserRepository()
    }

    DI.single<SettingsRepository> {
        LocalSettingsRepository()
    }

    DI.single<AuthUserUseCase<*>> {
        AuthUserUseCaseImpl()
    }

    DI.single<CheckTaskURLUseCase> {
        FakeCheckTaskURLUseCase()
    }

    DI.single {
        configGson()
    }
}
