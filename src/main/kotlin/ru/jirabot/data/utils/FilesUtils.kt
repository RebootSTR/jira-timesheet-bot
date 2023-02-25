package ru.jirabot.data.utils

import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths

object FilesUtils {

    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    /**
     * Работает как `touch` в линуксе: проверяет существование файла, если его нет - создает этот файл.
     * Выводит информацию в логи (зависимость конечно от логгера slf4j появляется :))
     */
    fun touch(url: String) {
        val path = Paths.get(url).toAbsolutePath()
        if (Files.exists(path)) {
            logger.info("File '$path' already exists.")
            return
        } else {
            val temp = path.toFile()
            temp.parentFile.mkdirs()
            if (temp.createNewFile())
                logger.info("File '$path' was created.")
            else
                logger.info("Whaaaaat file '$path' already exists.")
        }
    }

}
