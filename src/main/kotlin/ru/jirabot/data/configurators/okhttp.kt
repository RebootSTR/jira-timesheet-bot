package ru.jirabot.data.configurators

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory

fun configureOkHttp() = OkHttpClient.Builder()
    .addInterceptor(getLoggingInterceptors())
    .build()

private fun getLoggingInterceptors(): Interceptor = HttpLoggingInterceptor(createLogger()).apply {
    level = HttpLoggingInterceptor.Level.BODY
}

// фан-блин-факт: в kotlin-telegram-bot(6.0.7) логгер намертво прибит
// к HttpLoggingInterceptor с дефолтной реализацией (платформенный с level=INFO в System.err)
// com.github.kotlintelegrambot.network.ApiClient.kt (line 82)

// а вот slf4j настраиваемый в log4j.properties логгер для наших сервисов ретрофита(((
private const val LOGS_TAG = "OkHttp"
private val loggerForOkHttp = LoggerFactory.getLogger(LOGS_TAG)

private fun createLogger(): HttpLoggingInterceptor.Logger {
    return object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            loggerForOkHttp.debug(message)
        }
    }
}
