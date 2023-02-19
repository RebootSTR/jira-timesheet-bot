package ru.jirabot.domain.bot

import com.google.gson.annotations.SerializedName
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.model.User
import ru.jirabot.domain.serialization.Exclude

/**
 * Состояние бота для конкретного юзера
 * Состояние является звенов в цепочке алгортима общения с пользователем
 * Может храинить введенную информацию
 * Обязано обрабатывать действие пользователя и возвращать новое состояние
 *
 */
abstract class BotState(
    var messageId: Long? = null
) {

    @SerializedName(TYPE_VAL_NAME)
    val typeName: String = javaClass.name

    // need to inject after creating state
    @Exclude
    lateinit var dictionary: Dictionary

    @Exclude
    lateinit var client: Client

    /**
     * Определяет реакцию бота на устрановку состояния
     *
     * @return [BotState] если необходимо выполнить редирект
     * @return null если состояние принято успешно
     */
    abstract fun interactWithUser(user: User): BotState?

    /**
     * Обработка действия пользователя и возврат нового состояния
     */
    abstract fun obtainAction(action: UserAction): BotState

    companion object {
        const val TYPE_VAL_NAME = "type"
    }
}
