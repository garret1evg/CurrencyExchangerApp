package ua.chmutov.currencyexchangerapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "transaction"
)
data class TransactionModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    @ColumnInfo(name = "usr_id")
    val usrId: Long,

    @ColumnInfo(name = "from_wallet_id")
    val fromWalletId: Long,

    @ColumnInfo(name = "to_wallet_id")
    val toWalletId: Long,

    @ColumnInfo(name = "from_ex_rate")
    val fromExRate: Double,

    @ColumnInfo(name = "to_ex_rate")
    val toExRate: Double,

    @ColumnInfo(name = "from_amount")
    val fromAmount: Long,

    @ColumnInfo(name = "to_amount")
    val toAmount: Long,

    @ColumnInfo(name = "commission")
    val commission: Long,

    @ColumnInfo(name = "date")
    val date: LocalDate
)