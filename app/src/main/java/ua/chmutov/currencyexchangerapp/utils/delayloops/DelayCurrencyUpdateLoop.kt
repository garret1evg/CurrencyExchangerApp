package ua.chmutov.currencyexchangerapp.utils.delayloops

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import ua.chmutov.currencyexchangerapp.repository.MainRepository
import ua.chmutov.currencyexchangerapp.viewmodel.LoopState
import ua.chmutov.currencyexchangerapp.viewmodel.MainViewModel

class DelayCurrencyUpdateLoop(
    private val viewModel: MainViewModel,
    private val repository: MainRepository,
    private val dispatcher: CoroutineDispatcher
) : DelayLoop {
    @ExperimentalCoroutinesApi
    override fun loop(delay: Long) {
        val data = channelFlow<Unit> {
            while (!isClosedForSend) {
                if (viewModel.currencyUpdateLoopState.value is LoopState.Inactive) {
                    Timber.d("Loop stopped")
                    close()
                    return@channelFlow
                }
                Timber.d("Loop running")

                runCatching {
                    repository.refreshCurrencies()
                }.onFailure {
                    Timber.e(it)
                }.onSuccess {
                    Timber.d("Refresh currency succeed!")
                }.getOrNull()
                send(Unit)
                delay(delay)
            }
        }.flowOn(dispatcher)
        CoroutineScope(dispatcher).launch { data.collect {} }
    }
}