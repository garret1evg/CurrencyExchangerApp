package ua.chmutov.currencyexchangerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.chmutov.currencyexchangerapp.repository.MainRepository
import ua.chmutov.currencyexchangerapp.ui.model.Currency
import ua.chmutov.currencyexchangerapp.ui.model.Transaction
import ua.chmutov.currencyexchangerapp.ui.model.Wallet
import ua.chmutov.currencyexchangerapp.utils.comissioncontroller.CommissionControllerFirstXFree
import ua.chmutov.currencyexchangerapp.utils.delayloops.DelayCurrencyUpdateLoop
import java.time.LocalDate
import javax.inject.Inject

private const val DELAY_UPDATE_MILLIS = 5000L
private const val INIT_SELL_AMOUNT = "100.00"

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _transaction = MutableStateFlow<TransactionEvent>(TransactionEvent.Empty)
    val transaction: StateFlow<TransactionEvent> = _transaction

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
                    ?: Wallet(usrId = user.id, currency = currency.name, amount = 0))
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

    suspend fun resetEvent() {
        _transaction.emit(TransactionEvent.Empty)
    }

    fun trade() {
        viewModelScope.launch {
            runCatching {

                if (sellCurrency.firstOrNull() != null && sellCurrency.firstOrNull() == receiveCurrency.firstOrNull()) {
                    _transaction.emit(
                        TransactionEvent.SameCurrency(
                            sellCurrency.first()!!.name
                        )
                    )
                    return@launch
                }

                val user = currentUser.first()

                val fromCurrency = sellCurrency.first()!!
                val toCurrency = receiveCurrency.first()!!

                val commission =
                    CommissionControllerFirstXFree(currentUser.first().tradesNum)
                        .calculateCommission(sellAmount.first())
                val fromAmount = sellAmount.first()
                val toAmount = receiveAmount.first()
                val walletFrom = wallets.firstOrNull()
                    ?.firstOrNull { it.currency == fromCurrency.name && it.usrId == user.id }
                if (walletFrom == null || walletFrom.amount < fromAmount + commission) {
                    _transaction.emit(
                        TransactionEvent.NotEnoughMoney
                    )
                    return@launch
                }

                val walletTo = wallets.firstOrNull()
                    ?.firstOrNull { it.currency == toCurrency.name && it.usrId == user.id }
                val walletToId =
                    walletTo?.id ?: mainRepository.createWallet(
                        Wallet(
                            usrId = user.id,
                            currency = toCurrency.name
                        )
                    )

                val transaction = Transaction(
                    user.id,
                    walletFrom.id,
                    walletToId,
                    fromCurrency.exchangeRate,
                    toCurrency.exchangeRate,
                    fromAmount,
                    toAmount,
                    commission,
                    LocalDate.now()
                )
                mainRepository.createTransaction(transaction)
                transaction
            }.onFailure { ex ->
                _transaction.emit(
                    TransactionEvent.Failure
                )
            }.onSuccess {
                _transaction.emit(
                    TransactionEvent.Success(it)
                )
            }

        }

    }

}

sealed class LoopState {
    object Active : LoopState()
    object Inactive : LoopState()
}

sealed class TransactionEvent {
    class Success(val transaction: Transaction) : TransactionEvent()
    class SameCurrency(val currency: String) : TransactionEvent()
    object NotEnoughMoney : TransactionEvent()
    object Failure : TransactionEvent()
    object Empty : TransactionEvent()
}