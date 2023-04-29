package com.project.task.manager.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

fun String.capitalizeString() =
    if (this.isNotEmpty()) this[0].titlecase(Locale.getDefault()).plus(if (this.isNotEmpty()) this.substring(1).lowercase() else "") else this

fun String.formatTaskTime() =
    LocalDateTime.parse(this).run { "$dayOfMonth ${month.toString().capitalizeString()}, ${toLocalTime()}" }


fun datePickerDialog(context: Context, onSelect: (LocalDate) -> Unit): DatePickerDialog {
    val today = LocalDate.now()
   val dialog= DatePickerDialog(context, { _, year, month, day ->
        val date = LocalDate.of(year, month + 1, day)

        onSelect(date)
    }, today.year, today.monthValue-1, today.dayOfMonth)
    dialog.datePicker.minDate=System.currentTimeMillis()
    return dialog
}

fun timePickerDialog(
    context: Context,
    onSelect: (LocalTime) -> Unit
): TimePickerDialog {
    val now = LocalTime.now()
    return TimePickerDialog(context, { _, mHour: Int, mMinute: Int ->
        val time = LocalTime.of(mHour, mMinute)
        onSelect.invoke(time)
    }, now.hour, now.minute, false)

}