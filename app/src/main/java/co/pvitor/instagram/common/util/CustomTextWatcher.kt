package co.pvitor.instagram.common.util

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher(
    private val onTextChanged: (String) -> Unit
): TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged.invoke(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }

}