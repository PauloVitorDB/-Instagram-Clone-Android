package co.pvitor.instagram.register.data

import androidx.annotation.StringRes

interface RegisterCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(@StringRes message: Int?)
    fun onComplete()
}