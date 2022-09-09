package ua.chmutov.currencyexchangerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
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
    }.root

    inner class Presenter {

    }
}