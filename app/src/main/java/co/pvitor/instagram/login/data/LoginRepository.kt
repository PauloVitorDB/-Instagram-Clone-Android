package co.pvitor.instagram.login.data

import co.pvitor.instagram.common.model.UserAuth

class LoginRepository(
    private val dataSource: LoginDataSource
) {

    fun login(email: String, password: String, callback: LoginCallback<UserAuth>) {
        dataSource.login(email, password, callback)
    }

}