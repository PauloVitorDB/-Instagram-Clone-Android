package co.pvitor.instagram.login.data

import co.pvitor.instagram.common.util.RequestCallback

interface LoginDataSource {
    fun login(email: String, password: String, callback: RequestCallback<String?>)
}