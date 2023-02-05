package ru.jirabot.domain.bot

import kotlinx.serialization.Serializable
import ru.jirabot.domain.dictionary.Dictionary

/**
 * Состояние бота для конкретного юзера
 * Состояние является звенов в цепочке алгортима общения с пользователем
 * Может храинить введенную информацию
 * Обязано обрабатывать действие пользователя и возвращать новое состояние
 *
 */
@Serializable
abstract class BotState<User> {

    // need to inject after creating state
    lateinit var dictionary: Dictionary
    lateinit var client: Client<User>

    /**
     * Определяет реакцию бота на устрановку состояния
     *
     * @return [BotState] если необходимо выполнить редирект
     * @return null если состояние принято успешно
     */
    abstract fun interactWithUser(user: User): BotState<User>?

    /**
     * Обработка действия пользователя и возврат нового состояния
     */
    abstract fun obtainAction(action: UserAction): BotState<User>
}
