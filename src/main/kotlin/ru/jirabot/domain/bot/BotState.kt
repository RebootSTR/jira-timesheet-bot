package ru.jirabot.domain.bot

import com.google.gson.annotations.SerializedName
import ru.jirabot.domain.dictionary.Dictionary
import ru.jirabot.domain.model.User

/**
 * Состояние бота для конкретного юзера
 * Состояние является звенов в цепочке алгортима общения с пользователем
 * Может храинить введенную информацию
 * Обязано обрабатывать действие пользователя и возвращать новое состояние
 * Если messageId != null, то сообщения отправленные внутри стейта попытается заменить прошлое
 *
 */
abstract class BotState(
    var messageId: Long?
) {

    @SerializedName(TYPE_VAL_NAME)
    val typeName: String = javaClass.name

    /**
     * Интерфейс для коммуникации с пользователем
     */
    abstract fun getClient(): Client

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

    /**
     * Отправка сообщения с попыткой замены предыдущего по умолчанию.
     * Использовать [isReplaceMessage] только в крайнем случае.
     */
    fun sendMessage(
        user: User,
        text: String,
        buttons: List<List<Button>>? = null,
        isReplaceMessage: Boolean = true,
    ) {
        messageId = getClient().sendMessage(
            user = user,
            text = text,
            replaceMessageId = if (isReplaceMessage) messageId else null,
            buttons = buttons
        )
    }

    companion object {
        const val TYPE_VAL_NAME = "type"
    }
}
