package ru.jirabot.ui.terminal

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.data.dictionary.DictionaryImpl
import ru.jirabot.data.fakeUsecase.FakeAuthUserUseCase
import ru.jirabot.data.fakeUsecase.FakeCheckTaskURLUseCase
import ru.jirabot.data.repository.LocalSettingsRepository
import ru.jirabot.data.repository.LocalUserRepository
import ru.jirabot.data.database.datasource.AutoDropDataSource
import ru.jirabot.data.database.datasource.MyDataSource

fun configureTerminal() {
    DI.single<Dictionary> {
        DictionaryImpl()
    }

    DI.single<AuthUserUseCase<*>> {
        FakeAuthUserUseCase()
    }

    DI.single<CheckTaskURLUseCase> {
        FakeCheckTaskURLUseCase()
    }

    DI.single<Client<*>> {
        TerminalClient()
    }

    DI.single<SettingsRepository> {
        LocalSettingsRepository()
    }

    DI.single<MyDataSource> {
        AutoDropDataSource
    }

    DI.single<UserRepository<*>> {
        LocalUserRepository()
    }
}
