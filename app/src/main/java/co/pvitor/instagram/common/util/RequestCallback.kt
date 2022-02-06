package co.pvitor.instagram.common.util

interface RequestCallback<R> {
    fun onSuccess(response: R)
    fun onFailure()
    fun onComplete()
}