package co.pvitor.instagram.register

import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView

interface RegisterEmail {

    interface Presenter: BasePresenter {
        fun registerEmail(email: String)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayEmailFailure(@StringRes message: Int?)
        fun nextFragmentStep(email: String)
    }

}