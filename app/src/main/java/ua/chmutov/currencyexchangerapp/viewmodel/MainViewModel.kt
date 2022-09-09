package ua.chmutov.currencyexchangerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.chmutov.currencyexchangerapp.delayloops.DelayCurrencyUpdateLoop
import ua.chmutov.currencyexchangerapp.repository.MainRepository
import ua.chmutov.currencyexchangerapp.ui.model.Currency
import ua.chmutov.currencyexchangerapp.ui.model.Wallet
import javax.inject.Inject

private const val DELAY_UPDATE_MILLIS = 5000L
private const val INIT_SELL_AMOUNT = "100.00"

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val currencyUpdateLoopState = MutableStateFlow<LoopState>(LoopState.Active)

    val currentUser = mainRepository.getFirstUser()

    val currencyList = mainRepository.getCurrencies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val uiSellAmount = MutableStateFlow(INIT_SELL_AMOUNT)

    val sellAmount = uiSellAmount.map { (it.toDouble() * 100).toLong() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 1L)

    val sellCurrency = MutableStateFlow<Currency?>(null)

    val receiveCurrency = MutableStateFlow<Currency?>(null)

    val wallets = mainRepository.getWallets()

    val walletsItem = combine(wallets, currencyList, currentUser) { wallets, currencyList, user ->
        return@combine mutableListOf<Wallet>().apply {
            currencyList.forEach { currency ->
                add(wallets.firstOrNull { it.currency == currency.name && it.usrId == user.id }
                    ?: Wallet(user.id, currency.name, 0))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), mutableListOf())

    val receiveAmount = combine(
        sellAmount,
        sellCurrency,
        receiveCurrency
    ) { sellAmount, sellCurrency, receiveCurrency ->
        receiveCurrency ?: return@combine 0L
        sellCurrency ?: return@combine 0L
        return@combine (sellAmount * receiveCurrency.exchangeRate / sellCurrency.exchangeRate).toLong()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0L)

    val uiReceiveAmount = receiveAmount.map { "+ ${(it / 100.0)}" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "+ 0")

    init {
        DelayCurrencyUpdateLoop(this, mainRepository, Dispatchers.Default).loop(DELAY_UPDATE_MILLIS)
        viewModelScope.launch { mainRepository.createDefaultPreset() }

    }

    fun trade() {
        viewModelScope.launch {
            currentUser.firstOrNull()
                ?.let { mainRepository.updateUser(it.copy(tradesNum = it.tradesNum + 1)) }
        }

    }


}

sealed class LoopState {
    object Active : LoopState()
    object Inactive : LoopState()
}