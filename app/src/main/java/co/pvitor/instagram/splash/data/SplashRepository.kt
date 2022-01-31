package co.pvitor.instagram.splash.data

class SplashRepository(
    private val dataSource: SplashDataSource
) {

    fun authenticated(callback: SplashCallback) {
        dataSource.hasSessionUser(callback)
    }

}