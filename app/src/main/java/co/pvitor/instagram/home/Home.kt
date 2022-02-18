package co.pvitor.instagram.home

import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView
import co.pvitor.instagram.common.model.Post

interface Home {

    interface Presenter: BasePresenter {
        fun displayPosts()
    }

    interface View: BaseView<Home.Presenter> {
        fun displayEmptyFeed()
        fun displayFeed(feedList: List<Post>)
        fun displayError(@StringRes message: Int?)
        fun showProgress(enabled: Boolean)
    }

}