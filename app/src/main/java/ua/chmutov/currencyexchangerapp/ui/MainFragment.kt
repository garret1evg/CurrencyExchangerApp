package ua.chmutov.currencyexchangerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ua.chmutov.currencyexchangerapp.R
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
                is TransactionEvent.Success -> MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.convert_success_title))
                    .setMessage(
                        resources.getString(
                            R.string.convert_success,
                            (it.transaction.fromAmount / 100.0).toString(),
                            it.transaction.fromCurrency.name,
                            (it.transaction.toAmount / 100.0).toString(),
                            it.transaction.toCurrency.name,
                            (it.transaction.commission / 100.0).toString(),
                            it.transaction.fromCurrency.name
                        )
                    )
                    .setPositiveButton(resources.getString(R.string.done)) { _, _ -> }
                    .show()
                is TransactionEvent.SameCurrency -> MaterialAlertDialogBuilder(requireContext())
                    .setTitle(resources.getString(R.string.same_currency_title, it.currency))
                    .setMessage(
                        resources.getString(
                            R.string.same_currency
                        )
                    )
                    .setPositiveButton(resources.getString(R.string.ok)) { _, _ -> }
                    .show()
                is TransactionEvent.NotEnoughMoney -> Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.not_enough_money),
                    Toast.LENGTH_SHORT
                ).show()
                is TransactionEvent.Failure -> Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.error),
                    Toast.LENGTH_LONG
                ).show()
                else -> {}
            }
            viewModel.resetEvent()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }.root

    inner class Presenter {
        fun onSellCurrencySelected(position: Int) {
            lifecycleScope.launch {
                viewModel.sellCurrency.emit(viewModel.currencyList.firstOrNull()?.get(position))
            }

        }

        fun onReceiveCurrencySelected(position: Int) {
            lifecycleScope.launch {
                viewModel.receiveCurrency.emit(viewModel.currencyList.firstOrNull()?.get(position))
            }
        }

        fun onButtonClick() {
            viewModel.trade()
        }
    }
}