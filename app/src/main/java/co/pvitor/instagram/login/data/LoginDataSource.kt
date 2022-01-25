package co.pvitor.instagram.login.data

import co.pvitor.instagram.common.model.UserAuth

interface LoginDataSource {
    fun login(email: String, password: String, callback: LoginCallback<UserAuth>)
}