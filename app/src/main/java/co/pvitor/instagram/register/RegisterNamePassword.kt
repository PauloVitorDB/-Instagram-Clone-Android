package co.pvitor.instagram.register

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView

interface RegisterNamePassword {

    interface View: BaseView<RegisterNamePassword.Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameError(@StringRes message: Int?)
        fun displayPasswordError(@StringRes message: Int?)
        fun displayRegisterError(@StringRes message: Int?)
        fun nextStep(fragment: Fragment)
    }

    interface Presenter: BasePresenter {
        fun createUser(email: String, password: String, name: String)
    }

}