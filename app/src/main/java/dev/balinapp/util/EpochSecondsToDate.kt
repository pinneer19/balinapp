package dev.balinapp.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toDateString(): String {
    val date = Instant.fromEpochSeconds(this).toLocalDateTime(TimeZone.UTC).date
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')

    return "$day.$month.${date.year}"
}

fun Long.toDateStringWithTime(): String {
    val dateTime = Instant.fromEpochSeconds(this).toLocalDateTime(TimeZone.UTC)

    val date = dateTime.date
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')

    val time = dateTime.time
    val hour = time.hour.toString().padStart(2, '0')
    val minute = time.minute.toString().padStart(2, '0')

    return "$day.$month.${date.year} $hour:$minute"
}