package co.pvitor.instagram.register.data

import co.pvitor.instagram.common.model.UserAuth

interface RegisterDataSource {
    fun register(email: String, callback: RegisterCallback<String>)
    fun register(email: String, password: String, name: String, callback: RegisterCallback<UserAuth>)
}