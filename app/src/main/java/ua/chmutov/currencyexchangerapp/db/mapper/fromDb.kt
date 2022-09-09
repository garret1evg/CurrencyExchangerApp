package ua.chmutov.currencyexchangerapp.db.mapper

import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.ui.model.Currency

fun CurrencyModel.toCurrency() = Currency(name, exchangeRate, updatedTime)


fun List<CurrencyModel>.toCurrency() = map { it.toCurrency() }