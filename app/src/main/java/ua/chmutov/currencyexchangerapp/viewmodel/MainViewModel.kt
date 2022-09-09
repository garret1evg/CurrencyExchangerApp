package ua.chmutov.currencyexchangerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import ua.chmutov.currencyexchangerapp.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    init {
        refreshCurrency()
    }

    private fun refreshCurrency() = viewModelScope.launch {
        runCatching {
            mainRepository.refreshCurrencies()
        }.onFailure {
            Timber.e(it)
        }.onSuccess {
            Timber.d("Refresh currency succeed!")
        }.getOrNull()
    }
}