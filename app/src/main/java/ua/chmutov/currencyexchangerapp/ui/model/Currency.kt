package ua.chmutov.currencyexchangerapp.ui.model

import java.time.LocalDate

data class Currency(
    val name: String,
    val exchangeRate: Double,
    val updatedTime: LocalDate
)