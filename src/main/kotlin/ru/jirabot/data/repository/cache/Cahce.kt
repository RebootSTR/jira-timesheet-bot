package ru.jirabot.data.repository.cache

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

object Cache {

    private const val INVALIDATE_TIME = 60 * 60 * 5 // 5 minutes

    private val cache: ConcurrentMap<String, ObjectWithTime> = ConcurrentHashMap()

    /**
     * ВАЖНО: в качестве ключа можно использовать только то, что подходит в качестве ключа для Map.
     * ЛИБО: спиок из ключей, подходящих под правило выше
     */
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

/**
 * Опциональная аннотация для визуальной отметки закешированных функций
 */
@Target(AnnotationTarget.FUNCTION)
annotation class Cached