package ru.jirabot.data.repository.cache

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

object Cache {

    private const val INVALIDATE_TIME = 60 * 60 * 5 // 5 minutes

    private val cache: ConcurrentMap<Any, ObjectWithTime> = ConcurrentHashMap()

    fun <T> cached(
        key: Any,
        factory: () -> T
    ): T {
        val old = cache[key]
        return if (old == null) {
            val instance = factory()
            cache[key] = ObjectWithTime(instance as Any)
            instance
        } else {
            if (old.isNeedRecreate()) {
                val instance = factory()
                cache[key] = ObjectWithTime(instance as Any)
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
        keys.forEach { cache.remove(it) }
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