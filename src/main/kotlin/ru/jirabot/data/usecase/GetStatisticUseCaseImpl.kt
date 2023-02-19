package ru.jirabot.data.usecase

import ru.jirabot.di.DI
import ru.jirabot.domain.model.Statistic
import ru.jirabot.domain.model.User
import ru.jirabot.domain.repository.JiraRepository
import ru.jirabot.domain.repository.TemplateRepository
import ru.jirabot.domain.repository.UserRepository
import ru.jirabot.domain.usecase.GetStatisticUseCase
import java.time.LocalDate

class GetStatisticUseCaseImpl : GetStatisticUseCase {

    private val jiraRepository: JiraRepository = DI()
    private val userRepository: UserRepository = DI()
    private val templateRepository: TemplateRepository = DI()

    override fun invoke(user: User): Statistic {
        val auth = userRepository.getUserAuth(user)
        val jiraUser = jiraRepository.getUser(auth)

        return Statistic(
            name = jiraUser.name,
            templateCount = getTemplateCount(user),
            weekVisual = getWeekVisual(auth, jiraUser.login)
        )
    }

    private fun getTemplateCount(user: User) =
        templateRepository.getTemplates(user).size

    private fun getWeekVisual(auth: CharArray, login: String): String {
        val now = LocalDate.now()
        val weekStartDay = now.minusDays(now.dayOfWeek.value.toLong() - 1)
        val weekEndDay = weekStartDay.plusDays(6)
        val timeSheetsWithWeekends = jiraRepository.getTimeSheetsByDate(
            auth = auth,
            login = login,
            startDay = weekStartDay,
            endDay = weekEndDay
        )
        val weekends = timeSheetsWithWeekends.weekends
        val timeSheetMap = timeSheetsWithWeekends.timeSheets.groupBy { it.day }
        return buildString {
            for (i in 0 until 7) {
                append(days[i])
                append(TWO_DOTS_WITH_SPACE)
                val sheets = timeSheetMap[weekStartDay.plusDays(i.toLong())]
                if (sheets == null) {
                    if (weekends[i]) {
                        append(WEEKEND)
                    } else {
                        append(MINUS)
                    }
                } else {
                    val seconds = sheets.sumOf { it.spentTimeSeconds }
                    val minutes = seconds / 60.0
                    val hours = minutes / 60.0

                    append(hours.toInt())
                    append(SPACE)
                    append(HOURS)

                    if (hours.toInt() < hours) {
                        append(SPACE)
                        append((hours - hours.toInt()).toInt())
                        append(MINUTES)
                    }
                }
                append(LINE)
            }
        }
    }

    companion object {
        private const val SPACE = " "
        private const val MINUS = "-"
        private const val TWO_DOTS_WITH_SPACE = ": "
        private const val LINE = "\n"

        // todo dictionary
        private const val HOURS = "часов"
        private const val MINUTES = "минут"
        private const val WEEKEND = "выходной"
        private val days = listOf(
            "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"
        )
    }
}