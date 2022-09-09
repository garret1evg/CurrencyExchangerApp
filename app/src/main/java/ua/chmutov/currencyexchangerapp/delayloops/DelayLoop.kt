package ua.chmutov.currencyexchangerapp.delayloops

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface DelayLoop {
    @ExperimentalCoroutinesApi
    fun loop(delay: Long)
}