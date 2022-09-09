package ua.chmutov.currencyexchangerapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "wallet",
    indices = [
        Index(
            value = ["curr", "usr_id"],
            unique = true
        )
    ]
)
data class WalletModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "usr_id")
    val usrId: Long,

    @ColumnInfo(name = "curr")
    val currency: String,

    @ColumnInfo(name = "amount")
    val amount: Long
)