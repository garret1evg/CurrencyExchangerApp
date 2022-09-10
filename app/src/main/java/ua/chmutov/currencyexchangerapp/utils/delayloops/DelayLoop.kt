package ua.chmutov.currencyexchangerapp.utils.delayloops

import kotlinx.coroutines.ExperimentalCoroutinesApi

interface DelayLoop {
    @ExperimentalCoroutinesApi
    fun loop(delay: Long)
}