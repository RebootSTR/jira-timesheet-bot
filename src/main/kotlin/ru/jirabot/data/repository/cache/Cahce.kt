package ru.jirabot.data.repository.cache

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap


/**
 * Опциональная аннотация для визуальной отметки закешированных функций
 */
@Target(AnnotationTarget.FUNCTION)
annotation class Cached

object Cache {

    private const val INVALIDATE_TIME = 60 * 60 * 5 // 5 minutes

    private val cache: ConcurrentMap<String, ObjectWithTime> = ConcurrentHashMap()

    /**
     * Метод кэширует результат вызова функции на [INVALIDATE_TIME] секунд. Кэш можно инвалидировать с помощью
     * методов [invalidateAfter], передав те же ключи, что использовались при кешировании.
     *
     * ВАЖНО: в качестве ключа можно использовать только то, что подходит в качестве ключа для Map.
     * ЛИБО: спиок из ключей, подходящих под правило выше
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> cached(
        key: Any,
        factory: () -> T
    ): T {
        val mapKey: String = if (key is List<*>) {
            key.joinToString { it.toString() }
        } else {
            listOf(key).joinToString { it.toString() }
        }
        val old = cache[mapKey]
        return if (old == null) {
            val instance = factory()
            cache[mapKey] = ObjectWithTime(instance as Any)
            instance
        } else {
            if (old.isNeedRecreate()) {
                val instance = factory()
                cache[mapKey] = ObjectWithTime(instance as Any)
                instance
            } else {
                old.obj as T
            }
        }
    }

    /**
     * Позволяет инвалидировать кэш и до и после вызова
     */
    fun <T> invalidate(
        before: List<Any> = emptyList(),
        after: List<Any> = emptyList(),
        factory: () -> T
    ): T {
        invalidate(before)
        val obj = factory()
        invalidate(after)
        return obj
    }

    /**
     * Позволяет инвалидировать кэш после вызова
     */
    fun <T> invalidateAfter(
        after: List<Any>,
        factory: () -> T
    ): T {
        val obj = factory()
        invalidate(after)
        return obj
    }

    /**
     * Позволяет инвалидировать кэш до вызова
     */
    fun <T> invalidateBefore(
        before: List<Any>,
        factory: () -> T
    ): T {
        invalidate(before)
        return factory()
    }

    private fun invalidate(keys: List<Any>) {
        keys.forEach {
            if (it is List<*>) {
                val mapKey = it.joinToString { it.toString() }
                cache.remove(mapKey)
            } else {
                val mapKey = listOf(it).joinToString { it.toString() }
                cache.remove(mapKey)
            }
        }
    }

    private fun ObjectWithTime.isNeedRecreate(): Boolean {
        val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        return (now - this.time) > INVALIDATE_TIME
    }
}

class ObjectWithTime(
    val obj: Any,
    val time: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
)