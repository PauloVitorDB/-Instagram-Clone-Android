package co.pvitor.instagram.login.data

import android.os.Handler
import android.os.Looper

class FakeDataSource: LoginDataSource {

    override fun login(email: String, password: String, callback: LoginCallback) {

        Handler(Looper.getMainLooper()).postDelayed({

            if(email == "pvitor_duarte@gmail.com") {
                callback.onSuccess()
            } else {
                callback.onFailure("Error")
            }

            callback.onComplete()

        }, 3000)

    }

}