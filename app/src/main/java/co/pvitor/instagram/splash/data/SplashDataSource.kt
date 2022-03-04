package co.pvitor.instagram.splash.data

import co.pvitor.instagram.common.util.RequestCallback

interface SplashDataSource {
    fun hasSessionUser(callback: RequestCallback<String?>)
}