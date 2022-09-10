package ua.chmutov.currencyexchangerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ua.chmutov.currencyexchangerapp.databinding.FragmentMainBinding
import ua.chmutov.currencyexchangerapp.viewmodel.MainViewModel
import ua.chmutov.currencyexchangerapp.viewmodel.TransactionEvent

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

        myBalancesRecycler.adapter = WalletsAdapter(viewLifecycleOwner)

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

        viewModel.walletsItem.onEach {
            (myBalancesRecycler.adapter as? WalletsAdapter)?.submitList(
                it
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.transaction.onEach {
            when (it) {
                is TransactionEvent.Success -> Toast.makeText(
                    requireContext(),
                    "Success ${it.transaction}",
                    Toast.LENGTH_LONG
                ).show()
                is TransactionEvent.SameCurrency -> Toast.makeText(
                    requireContext(),
                    "SameCurrency ${it.currency}",
                    Toast.LENGTH_LONG
                ).show()
                is TransactionEvent.NotEnoughMoney -> Toast.makeText(
                    requireContext(),
                    "NotEnoughMoney",
                    Toast.LENGTH_LONG
                ).show()
                is TransactionEvent.Failure -> Toast.makeText(
                    requireContext(),
                    "Failure",
                    Toast.LENGTH_LONG
                ).show()
                else -> {}
            }
            viewModel.resetEvent()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }.root

    inner class Presenter {
        fun onSellCurrencySelected(position: Int) {
            viewModel.sellCurrency.value = viewModel.currencyList.value[position]
        }

        fun onReceiveCurrencySelected(position: Int) {
            viewModel.receiveCurrency.value = viewModel.currencyList.value[position]
        }

        fun onButtonClick() {
            viewModel.trade()
        }
    }
}