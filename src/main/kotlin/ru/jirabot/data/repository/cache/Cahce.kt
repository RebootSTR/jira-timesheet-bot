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

/**
 * Опциональная аннотация для визуальной отметки того, что функция инвалидирует кэш
 */
@Target(AnnotationTarget.FUNCTION)
annotation class InvalidateCache

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
     * Позволяет инвалидировать кэш после вызова
     */
    fun <T> invalidateAfter(
        vararg after: Any,
        factory: () -> T
    ): T {
        val obj = factory()
        after.forEach { invalidate(it) }
        return obj
    }

    /**
     * Позволяет инвалидировать кэш до вызова
     */
    fun <T> invalidateBefore(
        vararg before: Any,
        factory: () -> T
    ): T {
        before.forEach { invalidate(it) }
        return factory()
    }

    private fun invalidate(key: Any) {
        cache.remove(key.toMapKey())
    }

    private fun Any.toMapKey() = if (this is List<*>) {
        this.joinToString { it.toString() }
    } else {
        listOf(this).joinToString { it.toString() }
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