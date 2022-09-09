package ua.chmutov.currencyexchangerapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "currency",
    indices = [Index(value = ["name"], unique = true)]
)
data class CurrencyModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "ex_rate")
    val exchangeRate: Double,

    @ColumnInfo(name = "updated_time")
    val updatedTime: LocalDate
)