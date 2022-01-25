package co.pvitor.instagram.login.data

import android.os.Handler
import android.os.Looper
import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.UserAuth

class FakeDataSource: LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallback<UserAuth>) {

        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth: UserAuth? = Database.usersAuth.firstOrNull {
                email == it.email
            }

            when {
                userAuth == null -> callback.onFailure(R.string.email_not_found)
                userAuth.password != password -> callback.onFailure(R.string.wrong_password)
                else -> {
                    Database.sessionUserAuth = userAuth
                    callback.onSuccess(userAuth)
                }
            }

            callback.onComplete()

        }, 3000)

    }

}