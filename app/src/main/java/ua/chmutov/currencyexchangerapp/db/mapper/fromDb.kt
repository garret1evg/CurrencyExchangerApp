package ua.chmutov.currencyexchangerapp.db.mapper

import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.db.model.WalletModel
import ua.chmutov.currencyexchangerapp.ui.model.Currency
import ua.chmutov.currencyexchangerapp.ui.model.User
import ua.chmutov.currencyexchangerapp.ui.model.Wallet

fun CurrencyModel.toCurrency() = Currency(name, exchangeRate, updatedTime)

fun List<CurrencyModel>.toCurrency() = map { it.toCurrency() }

fun UserModel.toUser() = User(id, name, tradesNum)

fun WalletModel.toWallet() = Wallet(id ,usrId, currency, amount)

fun List<WalletModel>.toWallet() = map { it.toWallet() }