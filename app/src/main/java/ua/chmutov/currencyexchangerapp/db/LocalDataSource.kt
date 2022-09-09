package ua.chmutov.currencyexchangerapp.db

import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel

class LocalDataSource(
    private val database: AppDatabase
) {

    fun getCurrencies() = database.currencyDao().getCurrencies()

    suspend fun saveCurrencies(messages: List<CurrencyModel>) =
        database.currencyDao().insertCurrencies(messages)
}