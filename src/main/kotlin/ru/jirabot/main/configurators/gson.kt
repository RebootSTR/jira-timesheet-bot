package ru.jirabot.main.configurators

import com.google.gson.*
import ru.jirabot.domain.bot.BotState
import java.lang.reflect.Type

fun configGson(): Gson {
    val builder = GsonBuilder()
        .registerTypeAdapter(
            BotState::class.java,
            JsonDeserializerWithInheritance<BotState<*>>()
        )

    return builder.create()
}

/**
 * Использует сериализацию на основе базаового класса.
 * Для работы требует чтобы в родительском типе был параметр [BotState.TYPE_VAL_NAME] инициализированный
 * как название класса с помощью: getClass().getName()
 */
class JsonDeserializerWithInheritance<T> : JsonDeserializer<T> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): T {
        val jsonObject: JsonObject = json.asJsonObject
        val classNamePrimitive: JsonPrimitive = jsonObject.get(BotState.TYPE_VAL_NAME) as JsonPrimitive
        val className: String = classNamePrimitive.asString
        val clazz: Class<*> = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e.message)
        }
        return context.deserialize(jsonObject, clazz)
    }
}