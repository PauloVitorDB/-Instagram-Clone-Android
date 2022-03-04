package co.pvitor.instagram.splash.presentation

import co.pvitor.instagram.common.util.RequestCallback
import co.pvitor.instagram.splash.Splash
import co.pvitor.instagram.splash.data.SplashRepository

class SplashPresenter(
    private var _view: Splash.View?,
    private val repository: SplashRepository
): Splash.Presenter {

    private val view get() = _view!!

    override fun authenticated() {
        repository.authenticated(object: RequestCallback<String?> {

            override fun onSuccess(response: String?) {
                view.toMainScreen()
            }

            override fun onFailure() {
                view.toLoginScreen()
            }

        })
    }

    override fun onDestroy() {
        _view = null
    }

}