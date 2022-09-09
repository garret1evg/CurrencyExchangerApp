package ua.chmutov.currencyexchangerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ua.chmutov.currencyexchangerapp.databinding.FragmentMainBinding
import ua.chmutov.currencyexchangerapp.viewmodel.MainViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private val viewPresenter by lazy { Presenter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(layoutInflater).apply {
        lifecycleOwner = viewLifecycleOwner
        model = viewModel
        presenter = viewPresenter

        val receiveCurrencyAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            mutableListOf<String>()
        )
        receiveCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        currencyReceiveCurrencySpinner.adapter = receiveCurrencyAdapter

        val sellCurrencyAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            mutableListOf<String>()
        )
        sellCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        currencySellCurrencySpinner.adapter = sellCurrencyAdapter

        viewModel.currencyList.onEach { list ->
            receiveCurrencyAdapter.clear()
            receiveCurrencyAdapter.addAll(list.map { it.name })
            receiveCurrencyAdapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.currencyList.onEach { list ->
            sellCurrencyAdapter.clear()
            sellCurrencyAdapter.addAll(list.map { it.name })
            sellCurrencyAdapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }.root

    inner class Presenter {
        fun onSellCurrencySelected(position: Int) {
            viewModel.sellCurrency.value = viewModel.currencyList.value[position]
        }

        fun onReceiveCurrencySelected(position: Int) {
            viewModel.receiveCurrency.value = viewModel.currencyList.value[position]
        }

        fun onButtonClick(){
            viewModel.trade()
        }
    }
}