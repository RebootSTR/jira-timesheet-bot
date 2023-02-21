package ru.jirabot.data.usecase

import ru.jirabot.di.DI
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.*
import ru.jirabot.domain.usecase.FillTimeUseCase
import java.time.LocalDate
import java.time.ZoneId

class FillTimeUseCaseImpl : FillTimeUseCase {

    private val jiraRepository: JiraRepository = DI()
    private val userRepository: UserRepository = DI()
    private val templateRepository: TemplateRepository = DI()
    private val settingsRepository: SettingsRepository = DI()

    override fun invoke(user: User, templateId: Long?, comment: String?, day: LocalDate): Boolean {
        comment ?: run {
            println("FillTimeUseCaseImpl: COMMENT IS NULL")
            return false
        }

        templateId ?: run {
            println("FillTimeUseCaseImpl: templateId IS NULL")
            return false
        }

        val template = templateRepository.getTemplate(user, templateId) ?: run {
            println("FillTimeUseCaseImpl: template not found")
            return false
        }

        val hours = template.startTimeInMinutes / ONE_HOUR_MINUTES
        val minutes = template.startTimeInMinutes - hours * ONE_HOUR_MINUTES
        val timezone = settingsRepository.getSettingsValue(Settings.JIRA_TIMEZONE)

        val startTime = day
            .atTime(hours, minutes)
            .atZone(ZoneId.of(timezone))

        val auth = userRepository.getUserAuth(user)
        return jiraRepository.fillTime(
            auth = auth,
            taskName = template.taskName,
            startTime = startTime,
            // todo добавить возможность списать не ровное количество часов
            spentTimeSeconds = template.hours * ONE_HOUR_SECONDS,
            comment = comment
        )
    }

    companion object {
        private const val ONE_HOUR_SECONDS = 3600
        private const val ONE_HOUR_MINUTES = 60
    }
}