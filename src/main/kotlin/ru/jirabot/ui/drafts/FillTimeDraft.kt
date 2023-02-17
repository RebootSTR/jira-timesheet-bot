package ru.jirabot.ui.drafts

class FillTimeDraft(
    var fillTimeType: FillTimeType? = null,
    var templateId: Long? = null,
    var comment: String? = null,
)

enum class FillTimeType {
    ALL_WEEK,
    ONE_DAY
}