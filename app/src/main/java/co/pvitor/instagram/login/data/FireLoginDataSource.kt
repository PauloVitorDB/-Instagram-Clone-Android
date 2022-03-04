package co.pvitor.instagram.login.data

import co.pvitor.instagram.R
import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.auth.FirebaseAuth

class FireLoginDataSource: LoginDataSource {

    override fun login(email: String, password: String, callback: RequestCallback<String?>) {

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                callback.onFailure(R.string.invalid_login)
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
            .addOnCompleteListener { authResult ->
                if(authResult.result?.user != null) {
                    callback.onSuccess(email)
                } else {
                    callback.onFailure(R.string.invalid_login)
                }
            }

    }

}