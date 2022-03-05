package co.pvitor.instagram.add.data

import android.net.Uri
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback

interface AddDataSource {

    fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) {
        throw UnsupportedOperationException()
    }

    fun fetchSession(): User {
        throw UnsupportedOperationException()
    }

}