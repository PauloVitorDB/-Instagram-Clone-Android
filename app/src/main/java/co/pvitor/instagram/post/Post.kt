package co.pvitor.instagram.post

import android.net.Uri
import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView

interface Post {

    interface Presenter: BasePresenter {
        fun fetchPictures()
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(@StringRes message: Int?)
        fun displayEmptyPictures()
        fun displayPictures(pictureList: List<Uri>)
    }

}