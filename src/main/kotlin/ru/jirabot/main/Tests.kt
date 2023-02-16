package ru.jirabot.main

import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.domain.entities.User
import ru.jirabot.main.repository.sqlite.SqliteDataSource
import ru.jirabot.main.repository.sqlite.SqliteUserRepository
import ru.jirabot.main.states.InitState
import ru.jirabot.main.states.JiraAuthSuccess
import ru.jirabot.terminal.configureTerminal
import java.util.UUID

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

    /**
     * В процессе проверки работы бд)))))
     */
    fun testSqliteDb() {
        configureDi()
        val source = SqliteDataSource()
        val repo = SqliteUserRepository()

        val user = User(12L)

        val initState = repo.getUserState(user)
        require(initState is InitState) // should be 'InitState'

        val uuid = UUID.randomUUID().toString().substring(0..5)
        repo.saveUserAuth(user, uuid.toCharArray())
        require(repo.getUserAuth(user).contentEquals(uuid.toCharArray()))

        repo.saveUserState(user, JiraAuthSuccess())
        require(repo.getUserState(user) is JiraAuthSuccess)
    }
}
