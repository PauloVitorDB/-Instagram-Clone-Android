package co.pvitor.instagram.register.data

import android.net.Uri
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

    fun updateUserPhoto(uri: Uri, callback: RegisterCallback<Uri>) {
        dataSource.updateUserPhoto(uri, callback)
    }

}