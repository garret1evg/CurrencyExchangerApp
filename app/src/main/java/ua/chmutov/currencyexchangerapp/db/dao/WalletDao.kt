package ua.chmutov.currencyexchangerapp.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ua.chmutov.currencyexchangerapp.db.model.WalletModel

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallet")
    fun getWallets(): Flow<List<WalletModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWallet(wallet: WalletModel)

    @Update
    suspend fun updateWallet(user: WalletModel)
}