package ru.jirabot.ui

import ru.jirabot.data.configurators.configGson
import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.database.datasource.SqliteDataSource
import ru.jirabot.data.dictionary.DictionaryImpl
import ru.jirabot.data.repository.LocalSettingsRepository
import ru.jirabot.data.repository.sqlite.SqliteTemplateRepository
import ru.jirabot.data.repository.sqlite.SqliteUserRepository
import ru.jirabot.data.usecase.*
import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.TemplateRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.*

fun configureDi() {
    DI.single<Dictionary> {
        DictionaryImpl()
    }

    DI.single<UserRepository> {
        SqliteUserRepository()
    }

    DI.single<TemplateRepository> {
        SqliteTemplateRepository()
    }

    DI.single<SettingsRepository> {
        LocalSettingsRepository()
    }

    DI.single<AuthUserUseCase> {
        AuthUserUseCaseImpl()
    }

    DI.single<CheckTaskURLUseCase> {
        CheckTaskURLUseCaseImpl()
    }

    DI.single {
        configGson()
    }

    DI.single<MyDataSource> {
        SqliteDataSource
    }

    DI.single<ParseHoursInputUseCase> {
        ParseHoursInputUseCaseImpl()
    }

    DI.single<ParseStartTimeInputUseCase> {
        ParseStartTimeInputUseCaseImpl()
    }

    DI.single<SaveTemplateUseCase> {
        SaveTemplateUseCaseImpl()
    }

    DI.single<FillTimeUseCase> {
        FillTimeUseCaseImpl()
    }
}
