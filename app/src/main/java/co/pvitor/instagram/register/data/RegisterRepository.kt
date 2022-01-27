package co.pvitor.instagram.register.data

import co.pvitor.instagram.common.model.UserAuth

class RegisterRepository (
    private val dataSource: RegisterDataSource
) {

    fun register(email: String, callback: RegisterCallback<String>) {
        dataSource.register(email, callback)
    }

    fun register(email: String, password: String, name: String, callback: RegisterCallback<UserAuth>) {
        dataSource.register(email, password, name, callback)
    }

}