package ua.chmutov.currencyexchangerapp.ui.model

import java.time.LocalDate

data class Transaction(
    val usrId: Long,
    val fromWalletId: Long,
    val toWalletId: Long,
    val fromExRate: Double,
    val toExRate: Double,
    val fromAmount: Long,
    val toAmount: Long,
    val commission: Long,
    val date: LocalDate
)