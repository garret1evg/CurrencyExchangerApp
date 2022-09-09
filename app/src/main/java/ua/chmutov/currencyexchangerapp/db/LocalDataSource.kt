package ua.chmutov.currencyexchangerapp.db

import kotlinx.coroutines.flow.map
import ua.chmutov.currencyexchangerapp.db.mapper.toCurrency
import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel

class LocalDataSource(
    private val database: AppDatabase
) {

    fun getCurrencies() = database.currencyDao().getCurrencies().map { it.toCurrency() }

    suspend fun saveCurrencies(currencies: List<CurrencyModel>) =
        database.currencyDao().insertCurrencies(currencies)
}