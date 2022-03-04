package co.pvitor.instagram.splash.data

import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.util.RequestCallback

class LocalSplashDataSource: SplashDataSource {

    override fun hasSessionUser(callback: RequestCallback<String?>) {
        if(Database.sessionUserAuth != null) {
            callback.onSuccess(null)
        } else {
            callback.onFailure()
        }
    }

}