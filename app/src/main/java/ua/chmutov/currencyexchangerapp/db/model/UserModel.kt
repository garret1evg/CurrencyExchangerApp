package ua.chmutov.currencyexchangerapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val DEFAULT_NAME = "def-name"

@Entity(
    tableName = "user"
)
data class UserModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String = DEFAULT_NAME,

    @ColumnInfo(name = "trades_num")
    val tradesNum: Long = 0L
)