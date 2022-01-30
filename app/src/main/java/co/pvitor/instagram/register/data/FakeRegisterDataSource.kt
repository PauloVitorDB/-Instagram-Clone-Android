package co.pvitor.instagram.register.data

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
                password
            )

            val isUserRegistered = Database.usersAuth.add(userAuth)

            if(!isUserRegistered) {
                callback.onFailure(R.string.register_fail)
            } else {
                callback.onSuccess(userAuth)
            }

        }

        callback.onComplete()
    }

}