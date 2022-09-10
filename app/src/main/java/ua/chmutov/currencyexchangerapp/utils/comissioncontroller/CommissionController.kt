package ua.chmutov.currencyexchangerapp.utils.comissioncontroller

interface CommissionController {
    fun calculateCommission(amount: Long): Long
}