package co.pvitor.instagram.login.data

import androidx.annotation.StringRes

interface LoginCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(@StringRes message: Int?)
    fun onComplete()
}