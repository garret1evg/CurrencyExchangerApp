package ua.chmutov.currencyexchangerapp.utils.comissioncontroller

import ua.chmutov.currencyexchangerapp.constants.BASE_COMMISSION

private const val MORE_X_EURO_FREE = 200

class CommissionControllerMoreXEuroFree(private val exRate: Double) :
    CommissionController {
    override fun calculateCommission(amount: Long): Long =
        if (amount > MORE_X_EURO_FREE * exRate)
            0L
        else
            (amount * BASE_COMMISSION).toLong()
}
