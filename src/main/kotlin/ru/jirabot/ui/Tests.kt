package ru.jirabot.ui

import ru.jirabot.data.repository.sqlite.SqliteUserRepository
import ru.jirabot.data.database.datasource.MyDataSource
import ru.jirabot.data.repository.cache.Cache
import ru.jirabot.di.DI
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.SettingsRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.AuthUserUseCase
import ru.jirabot.domain.usecase.CheckTaskURLUseCase
import ru.jirabot.ui.states.logic2.InitState
import ru.jirabot.ui.states.logic2.auth.JiraAuthSuccess
import java.time.LocalDateTime
import java.time.ZoneOffset
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

    fun testCache() {
        // кешируем значения
        var cached = CacheTest.getTimeStamp()
        val second = CacheTest.getTimeStampSecond()

        Thread.sleep(2000)

        // значение закешировано, должны быть равны
        require(cached == CacheTest.getTimeStamp())
        // значение инвалидируется после вызова, значит должны быть равны
        require(cached == CacheTest.testInvalidateAfter())
        // значение успело инвалиднуться, больше они не равны
        require(cached != CacheTest.testInvalidateAfter())
        // кешируем значение заного
        cached = CacheTest.getTimeStamp()

        Thread.sleep(2000)

        // значение инвалидится до вызова, они не равны
        require(cached != CacheTest.testInvalidateBefore())

        // второе значение все еще закешировано, так как его никто не инвалидил, должны быть равны
        require(second == CacheTest.getTimeStampSecond())

        // кешируем значения с аргументами
        val withArg1 = CacheTest.timeStampWithArgument(1)
        Thread.sleep(2000)
        val withArg2 = CacheTest.timeStampWithArgument(2)

        // должны быть равны из-за кеша
        require(withArg1 == CacheTest.timeStampWithArgument(1))
        // не должны быть равны
        require(CacheTest.timeStampWithArgument(1) != CacheTest.timeStampWithArgument(2))
    }
}

object CacheTest {

    fun getTimeStamp() = Cache.cached(1) {
        return@cached LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }

    fun getTimeStampSecond() = Cache.cached(2) {
        return@cached LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }

    fun testInvalidateAfter() = Cache.invalidateAfter(
        after = listOf(1)
    ) {
        return@invalidateAfter getTimeStamp()
    }

    fun testInvalidateBefore() = Cache.invalidateBefore(
        before = listOf(1)
    ) {
        return@invalidateBefore getTimeStamp()
    }

    fun timeStampWithArgument(arg: Any) = Cache.cached(
        key = listOf(1, arg)
    ) {
        return@cached LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    }
}
