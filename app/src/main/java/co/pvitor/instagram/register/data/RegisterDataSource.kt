package co.pvitor.instagram.register.data

import android.net.Uri
import co.pvitor.instagram.common.model.UserAuth

interface RegisterDataSource {
    fun register(email: String, callback: RegisterCallback<String>)
    fun register(email: String, password: String, name: String, callback: RegisterCallback<UserAuth>)
    fun updateUserPhoto(uri: Uri, callback: RegisterCallback<Uri>)
}