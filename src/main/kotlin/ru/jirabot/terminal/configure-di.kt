package ru.jirabot.terminal

import ru.jirabot.di.DI
import ru.jirabot.domain.bot.Client
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.main.dictionary.DictionaryImpl
import ru.jirabot.main.fakeUsecase.FakeAuthUserUseCase
import ru.jirabot.main.fakeUsecase.FakeCheckTaskURLUseCase

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
}