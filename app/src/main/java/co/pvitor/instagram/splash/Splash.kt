package co.pvitor.instagram.splash

import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView

interface Splash {

    interface View: BaseView<Splash.Presenter> {
        fun toMainScreen()
        fun toLoginScreen()
    }

    interface Presenter: BasePresenter {
        fun authenticated()
    }

}