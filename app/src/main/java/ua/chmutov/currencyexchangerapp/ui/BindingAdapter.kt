package ua.chmutov.currencyexchangerapp.ui

import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputEditText

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