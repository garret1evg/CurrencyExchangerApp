package ua.chmutov.currencyexchangerapp.timex

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val zonedLocalDateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
val localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

private val localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

@JvmName("toLocalDateTimeNonNull")
fun String.toLocalDateTime(): LocalDateTime =
    when {
        this.last() == 'Z' -> ZonedDateTime.parse(this, zonedLocalDateTimeFormatter)
            .toLocalDateTime()
        else -> LocalDateTime.parse(this, localDateTimeFormatter)
    }

@JvmName("toLocalDateNonNull")
fun String.toLocalDate(): LocalDate =
    LocalDate.parse(this, localDateFormatter)