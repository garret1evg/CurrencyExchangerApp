package ua.chmutov.currencyexchangerapp.db

import kotlinx.coroutines.flow.map
import ua.chmutov.currencyexchangerapp.db.mapper.*
import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.db.model.WalletModel
import ua.chmutov.currencyexchangerapp.ui.model.Transaction
import ua.chmutov.currencyexchangerapp.ui.model.User
import ua.chmutov.currencyexchangerapp.ui.model.Wallet

class LocalDataSource(
    private val database: AppDatabase
) {

    fun getCurrencies() = database.currencyDao().getCurrencies().map { it.toCurrency() }

    suspend fun saveCurrencies(currencies: List<CurrencyModel>) =
        database.currencyDao().insertCurrencies(currencies)

    fun getFirstUser() = database.userDao().getFirstUser().map { it.toUser() }

    suspend fun saveUser(user: User) = database.userDao().insertUser(user.toUserModel())

    suspend fun createDefaultUser(userModel: UserModel) = database.userDao().insertUser(userModel)

    suspend fun updateUser(user: User) = database.userDao().updateUser(user.toUserModel())

    suspend fun createDefaultWallet(walletModel: WalletModel) =
        database.walletDao().insertWallet(walletModel)

    suspend fun createWallet(wallet: Wallet): Long {
        val walletModel = wallet.toWalletModel()
        database.walletDao().insertWallet(walletModel)
        return walletModel.id
    }

    fun getWallets() = database.walletDao().getWallets().map { it.toWallet() }

    suspend fun createTransaction(transaction: Transaction) =
        database.transactionDao().createTransaction(transaction.toTransactionModel())
}