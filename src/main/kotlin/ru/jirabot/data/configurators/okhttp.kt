package ru.jirabot.data.configurators

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun configureOkHttp() = OkHttpClient.Builder()
    .addInterceptor(getLoggingInterceptors())
    .build()

//todo починить вывод логов
private fun getLoggingInterceptors(): Interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}