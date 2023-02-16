package ru.jirabot.data

import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.data.configurators.configGson
import ru.jirabot.data.dictionary.DictionaryImpl
import ru.jirabot.data.fakeUsecase.FakeCheckTaskURLUseCase
import ru.jirabot.data.repository.LocalSettingsRepository
import ru.jirabot.data.database.SqliteUserRepository
import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.database.datasource.SqliteDataSource
import ru.jirabot.data.usecase.AuthUserUseCaseImpl

fun configureDi() {
    DI.single<Dictionary> {
        DictionaryImpl()
    }

    DI.single<UserRepository<*>> {
        SqliteUserRepository()
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

    DI.single<MyDataSource> {
        SqliteDataSource
    }
}
