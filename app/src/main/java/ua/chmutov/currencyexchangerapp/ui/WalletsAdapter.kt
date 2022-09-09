package ua.chmutov.currencyexchangerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.chmutov.currencyexchangerapp.R
import ua.chmutov.currencyexchangerapp.databinding.BalanceItemBinding
import ua.chmutov.currencyexchangerapp.ui.model.Wallet

class WalletsAdapter(
    val lifecycleOwner: LifecycleOwner,
) : ListAdapter<Wallet, WalletsAdapter.WalletHolder>(
    object : DiffUtil.ItemCallback<Wallet>() {
        override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet) =
            oldItem.usrId == newItem.usrId && oldItem.currency == newItem.currency

        override fun areContentsTheSame(
            oldItem: Wallet, newItem: Wallet
        ) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WalletHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.balance_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: WalletHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WalletHolder(
        val binding: BalanceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Wallet) {
            binding.item = item
            binding.lifecycleOwner = lifecycleOwner
            binding.executePendingBindings()
        }
    }
}
