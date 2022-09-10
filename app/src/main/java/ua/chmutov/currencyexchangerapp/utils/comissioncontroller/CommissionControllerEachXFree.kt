package ua.chmutov.currencyexchangerapp.utils.comissioncontroller

import ua.chmutov.currencyexchangerapp.constants.BASE_COMMISSION

private const val EACH_X_TRANSACTION_FREE = 10L

class CommissionControllerEachXFree(private val numTransactions: Long) :
    CommissionController {
    override fun calculateCommission(amount: Long): Long =
        if (numTransactions % EACH_X_TRANSACTION_FREE == 0L)
            0L
        else
            (amount * BASE_COMMISSION).toLong()
}
