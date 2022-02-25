package co.pvitor.instagram.add

import android.net.Uri
import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView

interface Add {

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestSuccess(@StringRes message: Int?)
        fun displayRequestFailure(@StringRes message: Int?)
    }

    interface Presenter: BasePresenter {
        fun createPost(uri: Uri, caption: String)
    }

}