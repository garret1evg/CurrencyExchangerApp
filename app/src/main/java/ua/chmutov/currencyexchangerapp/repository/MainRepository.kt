package ua.chmutov.currencyexchangerapp.repository

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import ua.chmutov.currencyexchangerapp.db.LocalDataSource
import ua.chmutov.currencyexchangerapp.db.mapper.toListCurrencyModel
import ua.chmutov.currencyexchangerapp.network.NetworkDataSource
import ua.chmutov.currencyexchangerapp.ui.model.User
import java.lang.Exception
import javax.inject.Singleton

@Singleton
class MainRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getCurrencies() = localDataSource.getCurrencies()

    suspend fun refreshCurrencies(){
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

    suspend fun createDefaultUser() = localDataSource.createDefaultUser()

    suspend fun updateUser(user: User) = localDataSource.updateUser(user)
}