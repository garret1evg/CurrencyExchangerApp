package ua.chmutov.currencyexchangerapp.network.model

data class CurrencyRatesResponse(
    val base: String,
    val date: String,
    val rates: HashMap<String, Double>
)