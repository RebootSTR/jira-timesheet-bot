package ru.jirabot.ui

import com.google.gson.Gson
import ru.jirabot.data.configurators.configGson
import ru.jirabot.data.configurators.configRetrofit
import ru.jirabot.data.configurators.configureOkHttp
import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.database.datasource.SqliteDataSource
import ru.jirabot.data.dictionary.DictionaryImpl
import ru.jirabot.data.repository.JiraRepositoryImpl
import ru.jirabot.data.repository.LocalSettingsRepository
import ru.jirabot.data.repository.sqlite.SqliteTemplateRepository
import ru.jirabot.data.repository.sqlite.SqliteUserRepository
import ru.jirabot.data.services.com.deniz.jira.worklog.CalendarService
import ru.jirabot.data.services.jira.JiraService
import ru.jirabot.data.usecase.*
import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.*
import ru.jirabot.domain.usecase.*

fun configureDi() {
    dictionary()
    dataSource()
    repositories()
    gson()
    useCases()
    retrofit()
}

fun dictionary() {

    DI.single<Dictionary> {
        DictionaryImpl()
    }
}

fun dataSource() {

    DI.single<MyDataSource> {
        SqliteDataSource
    }
}

fun repositories() {
    DI.single<UserRepository> {
        SqliteUserRepository()
    }

    DI.single<TemplateRepository> {
        SqliteTemplateRepository()
    }

    DI.single<SettingsRepository> {
        LocalSettingsRepository()
    }

    DI.single<JiraRepository> {
        JiraRepositoryImpl()
    }
}

fun gson() {
    DI.single {
        configGson()
    }
}

fun useCases() {

    DI.single<AuthUserUseCase> {
        AuthUserUseCaseImpl()
    }

    DI.single<CheckTaskURLUseCase> {
        CheckTaskURLUseCaseImpl()
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

    DI.single<GetStatisticUseCase> {
        GetStatisticUseCaseImpl()
    }
}

fun retrofit() {
    val repository: SettingsRepository = DI()
    val gson: Gson = DI()
    val okHttp = configureOkHttp()
    val retrofit = configRetrofit(
        repository.getSettingsValue(Settings.JIRA_HOST),
        gson,
        okHttp
    )

    DI.single {
        retrofit.create(JiraService::class.java)
    }

    DI.single {
        retrofit.create(CalendarService::class.java)
    }
}
