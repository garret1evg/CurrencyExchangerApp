package ua.chmutov.currencyexchangerapp.db.mapper

import ua.chmutov.currencyexchangerapp.db.model.CurrencyModel
import ua.chmutov.currencyexchangerapp.db.model.TransactionModel
import ua.chmutov.currencyexchangerapp.db.model.UserModel
import ua.chmutov.currencyexchangerapp.db.model.WalletModel
import ua.chmutov.currencyexchangerapp.network.model.CurrencyRatesResponse
import ua.chmutov.currencyexchangerapp.timex.toLocalDate
import ua.chmutov.currencyexchangerapp.ui.model.Transaction
import ua.chmutov.currencyexchangerapp.ui.model.User
import ua.chmutov.currencyexchangerapp.ui.model.Wallet

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

fun User.toUserModel() = UserModel(id, name, tradesNum)

fun Wallet.toWalletModel() = WalletModel(usrId = usrId, currency = currency, amount = amount)

fun Transaction.toTransactionModel() = TransactionModel(
    usrId = usrId,
    fromWalletId = fromWalletId,
    toWalletId = toWalletId,
    fromExRate = fromCurrency.exchangeRate,
    toExRate = toCurrency.exchangeRate,
    fromAmount = fromAmount,
    toAmount = toAmount,
    commission = commission,
    date = date
)