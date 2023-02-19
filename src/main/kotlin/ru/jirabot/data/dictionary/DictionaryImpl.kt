package ru.jirabot.data.dictionary

import ru.jirabot.domain.dictionary.Dictionary

class DictionaryImpl: Dictionary {

    override fun get(key: String): String =
        if (dictionaryMap.containsKey(key)) {
            dictionaryMap[key]!!
        } else {
            key
        }
}

private val dictionaryMap = mapOf(
    "HelloState" to "Привет! Этот бот нужен для чего-то там",
    "UsernameInputState" to "Введи свое имя пользователя",
    "PasswordInputState" to "Введи свой пароль",
    "JiraAuthState" to "фывфывыфв",
    "JiraAuthSuccess" to "Авторизация успешна!",
    "FirstTemplateState" to "Давай создадим твой первый шаблон для заполнения таймшита",
    "TaskURLInputState" to "Отправь ссылку на задачу в Jira",
    "CheckURLState" to "фывфывыфв",
    "TaskNameInputState" to "Назови задачу",
    "TaskHoursInputState" to "Сколько часов в день логировать?",
    "HoursValidateState" to "фывфывыфв",
    "TaskStartTimeInputState" to "Во сколько часов начинать рабочий день?",
    "TaskStartTimeValidateState" to "фывфывыфв",
    "TaskStartTimeErrorState" to "Введена херня",
    "TaskHoursErrorState" to "Введена херня",
    "WrongURLState" to "Введена не правильная ссылка",
    "JiraAuthErrorState" to "Авторизация не удалась. \nДавай попробуем еще раз",
    "TesterInputState" to "Ничего не нажато",
    "MenuState" to "Привет, %s\n\nСоздано шаблонов - %d\n\nТекущая неделя:\n\n%s"
)