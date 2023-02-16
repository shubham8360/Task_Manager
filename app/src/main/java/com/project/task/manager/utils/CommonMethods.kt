package com.project.task.manager.utils

import java.time.LocalDateTime
import java.util.*

fun String.capitalizeString() =
    if (this.isNotEmpty()) this[0].titlecase(Locale.getDefault()).plus(if (this.isNotEmpty()) this.substring(1).lowercase() else "") else this

fun String.formatTaskTime() =
    LocalDateTime.parse(this).run { "$dayOfMonth ${month.toString().capitalizeString()}, ${toLocalTime()}" }
