package ua.chmutov.currencyexchangerapp.ui

import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputEditText
import ua.chmutov.currencyexchangerapp.ui.model.Wallet

@BindingAdapter("editableItemText")
fun bindSetEditableItemText(view: TextInputEditText, enteredText: String) = view.apply {
    enteredText.let {
        if (text.toString() != it) {
            setText(it)
        }
    }
}
@InverseBindingAdapter(attribute = "editableItemText")
fun bindGetEditableItemText(view: TextInputEditText): String {
    return view.text.toString()
}

@BindingAdapter("editableItemTextAttrChanged")
fun bindSetEditableItemTextAttrChanged(
    view: TextInputEditText,
    attrChange: InverseBindingListener?
) = view.apply {
    attrChange?.let {
        doOnTextChanged { _, _, _, _ ->
            it.onChange()
        }
    }
}

@BindingAdapter("setWalletInfo")
fun bindSetWalletInfo(
    view: TextView,
    wallet: Wallet?
) = view.apply {
    wallet?.let {
        text = "${it.amount/100.00} ${it.currency}"
    }
}