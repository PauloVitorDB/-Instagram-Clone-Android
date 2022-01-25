package co.pvitor.instagram.login

import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BaseView
import co.pvitor.instagram.common.base.BasePresenter

interface Login {

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes message: Int?)
        fun displayPasswordFailure(@StringRes message: Int?)
        fun onUserAuthenticate()
        fun onUserUnauthorized(@StringRes message: Int?)
    }

    interface Presenter: BasePresenter {
        fun login(email: String, password: String)
    }

}
