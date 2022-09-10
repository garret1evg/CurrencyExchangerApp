package ua.chmutov.currencyexchangerapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "currency"
)
data class CurrencyModel(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "ex_rate")
    val exchangeRate: Double,

    @ColumnInfo(name = "updated_time")
    val updatedTime: LocalDate
)