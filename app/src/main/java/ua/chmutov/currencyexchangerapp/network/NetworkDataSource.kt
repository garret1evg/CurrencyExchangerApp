package ua.chmutov.currencyexchangerapp.network

class NetworkDataSource(
    private val currencyRatesApi: CurrencyRatesApi
) {
    suspend fun getCurrencyRates() = currencyRatesApi.getCurrencyRates()
}