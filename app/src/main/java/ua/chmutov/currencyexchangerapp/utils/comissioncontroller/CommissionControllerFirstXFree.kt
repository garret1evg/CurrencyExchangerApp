package ua.chmutov.currencyexchangerapp.utils.comissioncontroller

import ua.chmutov.currencyexchangerapp.constants.BASE_COMMISSION

private const val NUM_FREE_TRANSACTIONS = 5L

class CommissionControllerFirstXFree(private val numTransactions: Long) :
    CommissionController {
    override fun calculateCommission(amount: Long): Long =
        if (numTransactions < NUM_FREE_TRANSACTIONS)
            0L
        else
            (amount * BASE_COMMISSION).toLong()
}
