package ua.chmutov.currencyexchangerapp.ui.model

data class Wallet(
    val id: Long = -1L,
    val usrId: Long,
    val currency: String,
    val amount: Long
)