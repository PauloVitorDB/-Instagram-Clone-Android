package co.pvitor.instagram.splash.data

import co.pvitor.instagram.common.model.Database

class LocalSplashDataSource: SplashDataSource {

    override fun hasSessionUser(callback: SplashCallback) {
        if(Database.sessionUserAuth != null) {
            callback.onSuccess()
        } else {
            callback.onFailure()
        }
    }

}