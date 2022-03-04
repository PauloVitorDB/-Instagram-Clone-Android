package co.pvitor.instagram.login.data

import co.pvitor.instagram.common.util.RequestCallback

class LoginRepository(
    private val dataSource: LoginDataSource
) {

    fun login(email: String, password: String, callback: RequestCallback<String?>) {
        dataSource.login(email, password, callback)
    }

}