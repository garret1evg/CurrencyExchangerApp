package ua.chmutov.currencyexchangerapp.repository

import timber.log.Timber
import ua.chmutov.currencyexchangerapp.constants.BASE_AMOUNT
import ua.chmutov.currencyexchangerapp.constants.BASE_CURRENCY
import ua.chmutov.currencyexchangerapp.db.LocalDataSource
import ua.chmutov.currencyexchangerapp.db.mapper.toListCurrencyModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.db.model.WalletModel
import ua.chmutov.currencyexchangerapp.network.NetworkDataSource
import ua.chmutov.currencyexchangerapp.ui.model.User
import javax.inject.Singleton

@Singleton
class MainRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getCurrencies() = localDataSource.getCurrencies()

    suspend fun refreshCurrencies() {
        try {
            val response = networkDataSource.getCurrencyRates()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                localDataSource.saveCurrencies(result.toListCurrencyModel())
            } else {
                Timber.e(response.message())
            }
        } catch (e: Exception) {
            Timber.e(e.message ?: "An Error occured")
        }
    }

    fun getFirstUser() = localDataSource.getFirstUser()

    suspend fun createDefaultPreset() {
        val newUser = UserModel()
        val currency = BASE_CURRENCY
        val newWallet = WalletModel(usrId = newUser.id, currency = currency, amount = BASE_AMOUNT)
        localDataSource.createDefaultUser(newUser)
        localDataSource.createDefaultWallet(newWallet)
    }

    suspend fun updateUser(user: User) = localDataSource.updateUser(user)

    fun getWallets() = localDataSource.getWallets()
}