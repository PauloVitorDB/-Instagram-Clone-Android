package co.pvitor.instagram.register.data

import android.net.Uri
import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.UserAuth
import java.util.*

class FakeRegisterDataSource: RegisterDataSource {

    override fun register(email: String, callback: RegisterCallback<String>) {

        val userAuth: UserAuth? = Database.usersAuth.firstOrNull {
            email == it.email
        }

        when {
            userAuth != null -> callback.onFailure(R.string.email_alredy_exists)
            else -> callback.onSuccess(email)
        }

        callback.onComplete()
    }

    override fun register(
        email: String,
        password: String,
        name: String,
        callback: RegisterCallback<UserAuth>
    ) {

        val userExists: UserAuth? = Database.usersAuth.firstOrNull {
            email == it.email
        }

        if(userExists != null) {
            callback.onFailure(R.string.register_error_user_exists)
        } else {

            val userAuth = UserAuth(
                UUID.randomUUID().toString(),
                name,
                email,
                password,
                null,
                0, 0, 0
            )

            val isUserRegistered = Database.usersAuth.add(userAuth)
            Database.sessionUserAuth = userAuth

            Database.posts[userAuth.uuid] = mutableSetOf()
            Database.feedList[userAuth.uuid] = mutableSetOf()
            Database.followers[userAuth.uuid] = setOf()

            if(!isUserRegistered) {
                callback.onFailure(R.string.register_fail)
            } else {

                callback.onSuccess(userAuth)
            }

        }

        callback.onComplete()
    }

    override fun updateUserPhoto(uri: Uri, callback: RegisterCallback<Uri>) {

        val userAuth = Database.sessionUserAuth

        if(userAuth == null) {
            callback.onFailure(R.string.session_user_not_found)
        } else {

            val index = Database.usersAuth.indexOf(Database.sessionUserAuth)
            Database.usersAuth[index] = Database.sessionUserAuth!!.copy(
                profilePhoto = uri
            )
            Database.sessionUserAuth = Database.usersAuth[index]

            callback.onSuccess(uri)
        }

        callback.onComplete()
    }

}