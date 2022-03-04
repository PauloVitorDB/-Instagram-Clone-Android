package co.pvitor.instagram.splash.data

import co.pvitor.instagram.common.util.RequestCallback
import com.google.firebase.auth.FirebaseAuth

class FireSplashDataSource: SplashDataSource {

    override fun hasSessionUser(callback: RequestCallback<String?>) {
        if(FirebaseAuth.getInstance().uid == null) {
            callback.onFailure()
        } else {
            callback.onSuccess(null)
        }
    }

}