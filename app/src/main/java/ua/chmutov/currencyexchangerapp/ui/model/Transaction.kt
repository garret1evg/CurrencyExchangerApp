package ua.chmutov.currencyexchangerapp.ui.model

import java.time.LocalDate

data class Transaction(
    val usrId: Long,
    val fromWalletId: Long,
    val toWalletId: Long,
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val fromAmount: Long,
    val toAmount: Long,
    val commission: Long,
    val date: LocalDate
)