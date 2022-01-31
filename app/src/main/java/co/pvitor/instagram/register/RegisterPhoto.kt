package co.pvitor.instagram.register

import android.net.Uri
import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView

interface RegisterPhoto {

    interface View: BaseView<RegisterPhoto.Presenter> {
        fun showProgress(enabled: Boolean)
        fun onUpdateError(@StringRes message: Int?)
        fun onUpdateSuccess(uri: Uri)
    }

    interface Presenter: BasePresenter {
        fun updateUserPhoto(uri: Uri)
    }

}