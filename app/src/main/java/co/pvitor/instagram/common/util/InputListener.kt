package co.pvitor.instagram.common.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class InputListener {
    companion object {
        fun hideInputOnClick(view: View) {
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}