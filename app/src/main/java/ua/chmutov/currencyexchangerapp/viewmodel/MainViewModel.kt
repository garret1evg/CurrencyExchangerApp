package ua.chmutov.currencyexchangerapp.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import ua.chmutov.currencyexchangerapp.delayloops.DelayCurrencyUpdateLoop
import ua.chmutov.currencyexchangerapp.repository.MainRepository
import javax.inject.Inject

private const val DELAY_UPDATE_MILLIS = 5000L

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val currencyUpdateLoopState = MutableStateFlow<LoopState>(LoopState.Active)

    init {
        DelayCurrencyUpdateLoop(this, mainRepository, Dispatchers.Default).loop(DELAY_UPDATE_MILLIS)
    }

}

sealed class LoopState {
    object Active : LoopState()
    object Inactive : LoopState()
}