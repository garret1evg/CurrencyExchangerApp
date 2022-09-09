package ua.chmutov.currencyexchangerapp.db.mapper

import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.ui.model.Currency
import ua.chmutov.currencyexchangerapp.ui.model.User

fun CurrencyModel.toCurrency() = Currency(name, exchangeRate, updatedTime)


fun List<CurrencyModel>.toCurrency() = map { it.toCurrency() }

fun UserModel.toUser() = User(id, name, tradesNum)