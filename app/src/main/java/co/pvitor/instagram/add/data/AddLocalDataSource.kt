package co.pvitor.instagram.add.data

import android.net.Uri
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback
import java.lang.RuntimeException

class AddLocalDataSource: AddDataSource {

    override fun createPost(
        userUUID: String,
        uri: Uri,
        caption: String,
        callback: RequestCallback<Boolean>
    ) {

    }

    override fun fetchSession(): User {
        return Database.sessionUserAuth ?: throw RuntimeException()
    }

}