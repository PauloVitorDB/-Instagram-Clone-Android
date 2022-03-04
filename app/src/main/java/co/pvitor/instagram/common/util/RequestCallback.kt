package co.pvitor.instagram.common.util

import androidx.annotation.StringRes

interface RequestCallback<R> {
    fun onSuccess(response: R)
    fun onFailure() {}
    fun onFailure(@StringRes message: Int?) {}
    fun onComplete() {}
}