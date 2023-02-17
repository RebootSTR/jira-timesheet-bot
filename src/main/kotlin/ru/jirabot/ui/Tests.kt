package ru.jirabot.ui

import ru.jirabot.data.repository.sqlite.SqliteUserRepository
import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.entities.User
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.ui.states.logic2.InitState
import ru.jirabot.ui.states.logic2.JiraAuthSuccess
import java.util.*

object Tests {

    /**
     * Если не упало, значит работает))))
     */
    fun testDi() {
        configureDi()

        DI.get<Dictionary>()
        DI.get<AuthUserUseCase>()
        DI.get<CheckTaskURLUseCase>()
        DI.get<UserRepository>()
        DI.get<SettingsRepository>()
    }

    /**
     * В процессе проверки работы бд)))))
     */
    fun testSqliteDb() {
        configureDi()
        val source = DI<MyDataSource>().create()
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
