package ru.jirabot.data.configurators

import com.google.gson.*
import ru.jirabot.domain.bot.BotState
import ru.jirabot.domain.serialization.Exclude
import java.lang.reflect.Type
import java.time.LocalDateTime


fun configGson(): Gson {
    val builder = GsonBuilder()
        .registerTypeAdapter(
            BotState::class.java,
            JsonDeserializerWithInheritance<BotState>()
        )
        .registerTypeAdapter(LocalDateTime::class.java, dateTimeSerializer())
        .setExclusionStrategies(exclusionStrategy())

    return builder.create()
}

private fun exclusionStrategy() = object : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }

    override fun shouldSkipField(field: FieldAttributes): Boolean {
        return field.getAnnotation(Exclude::class.java) != null
    }
}

private fun dateTimeSerializer() = JsonDeserializer { json, _, _ ->
    LocalDateTime.parse(json.asString)
}

/**
 * Использует сериализацию на основе базового класса.
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
