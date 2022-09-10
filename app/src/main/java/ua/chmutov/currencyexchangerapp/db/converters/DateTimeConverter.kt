package ua.chmutov.currencyexchangerapp.db.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConverter {

    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String?) =
        value?.let { DateTimeFormatter.ISO_LOCAL_DATE.parse(value, LocalDate::from) }

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? =
        date?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(value: String?) =
        value?.let { DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(value, LocalDateTime::from) }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(date: LocalDateTime?): String? =
        date?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

}