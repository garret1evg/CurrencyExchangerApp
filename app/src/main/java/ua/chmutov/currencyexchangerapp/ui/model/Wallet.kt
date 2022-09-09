package ua.chmutov.currencyexchangerapp.ui.model

data class Wallet(
    val usrId: Long,
    val currency: String,
    val amount: Long
)