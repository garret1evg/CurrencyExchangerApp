package ua.chmutov.currencyexchangerapp.db.dao

import androidx.room.*
import ua.chmutov.currencyexchangerapp.db.model.TransactionModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel

@Dao
interface TransactionDao {

    @Query("UPDATE user SET trades_num = trades_num + 1 WHERE id = :userId ")
    suspend fun increaseUserNumTrade(userId: Long)

    @Query("UPDATE wallet SET amount = amount + :amount WHERE id = :walletId ")
    suspend fun updateWalletAmount(walletId: Long, amount: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: TransactionModel)

    @Transaction
    suspend fun createTransaction(transactionModel: TransactionModel) {
        increaseUserNumTrade(transactionModel.usrId)
        updateWalletAmount(transactionModel.fromWalletId, -transactionModel.fromAmount)
        updateWalletAmount(transactionModel.toWalletId, transactionModel.toAmount)
        insertTransaction(transactionModel)
    }
}