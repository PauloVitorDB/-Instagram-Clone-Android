package co.pvitor.instagram.splash.data

import co.pvitor.instagram.common.util.RequestCallback

class SplashRepository(
    private val dataSource: SplashDataSource
) {

    fun authenticated(callback: RequestCallback<String?>) {
        dataSource.hasSessionUser(callback)
    }

}