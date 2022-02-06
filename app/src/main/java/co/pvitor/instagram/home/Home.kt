package co.pvitor.instagram.home

import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView
import co.pvitor.instagram.common.model.Post

interface Home {

    interface View: BaseView<Home.Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(@StringRes message: Int?)
        fun displayPosts(posts: List<Post>)
    }

    interface Presenter: BasePresenter {
        fun fetchHomePosts()
    }

}