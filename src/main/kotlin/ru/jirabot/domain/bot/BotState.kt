package ru.jirabot.domain.bot

import ru.jirabot.domain.Client
import ru.jirabot.domain.UserAction
import ru.jirabot.domain.dictionary.Dictionary

/**
 * Состояние бота для конкретного юзера
 * Состояние является звенов в цепочке алгортима общения с пользователем
 * Может храинить введенную информацию
 * Обязано обрабатывать действие пользователя и возвращать новое состояние
 *
 */
abstract class BotState {

    // need to inject after creating state
    lateinit var dictionary: Dictionary
    lateinit var client: Client

    /**
     * Определяет реакцию бота на устрановку состояния
     *
     * @return [BotState] если необходимо выполнить редирект
     * @return null если состояние принято успешно
     */
    abstract fun interactWithUser(): BotState?

    /**
     * Обработка действия пользователя и возврат нового состояния
     */
    abstract fun obtainAction(action: UserAction): BotState
}