package ua.chmutov.currencyexchangerapp.db.callbacks

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ua.chmutov.currencyexchangerapp.constants.BASE_AMOUNT
import ua.chmutov.currencyexchangerapp.constants.BASE_CURRENCY
import ua.chmutov.currencyexchangerapp.db.dao.UserDao
import ua.chmutov.currencyexchangerapp.db.dao.WalletDao
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.db.model.WalletModel
import javax.inject.Provider

class InitRecordsCallback(
    private val userProvider: Provider<UserDao>,
    private val walletProvider: Provider<WalletDao>
) : RoomDatabase.Callback() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        val newUser = UserModel()
        val currency = BASE_CURRENCY
        val newWallet = WalletModel(usrId = newUser.id, currency = currency, amount = BASE_AMOUNT)
        userProvider.get().insertUser(newUser)
        walletProvider.get().insertWallet(newWallet)
    }
}