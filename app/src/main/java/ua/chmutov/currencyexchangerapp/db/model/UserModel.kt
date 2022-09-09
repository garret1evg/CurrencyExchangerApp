package ua.chmutov.currencyexchangerapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.chmutov.currencyexchangerapp.constants.DEFAULT_USER_NAME

@Entity(
    tableName = "user"
)
data class UserModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 1L,

    @ColumnInfo(name = "name")
    val name: String = DEFAULT_USER_NAME,

    @ColumnInfo(name = "trades_num")
    val tradesNum: Long = 0L
)