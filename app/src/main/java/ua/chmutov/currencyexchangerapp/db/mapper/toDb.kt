package ua.chmutov.currencyexchangerapp.db.mapper

import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.network.model.CurrencyRatesResponse
import ua.chmutov.currencyexchangerapp.timex.toLocalDate

fun CurrencyRatesResponse.toListCurrencyModel() = mutableListOf<CurrencyModel>().apply {
    add(CurrencyModel(name = base, updatedTime = date.toLocalDate(), exchangeRate = 1.0))
    rates.forEach {
        add(
            CurrencyModel(
                name = it.key,
                updatedTime = date.toLocalDate(),
                exchangeRate = it.value
            )
        )
    }
}