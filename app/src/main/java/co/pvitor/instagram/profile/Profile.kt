package co.pvitor.instagram.profile

import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth

interface Profile {
    
    interface View: BaseView<Profile.Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserPosts(posts: List<Post>)
        fun displayEmptyPosts()
        fun displayUserProfile(user: Pair<UserAuth, Boolean?>)
        fun displayRequestFailure(@StringRes message: Int?)
    }

    interface Presenter: BasePresenter {
        fun clear()
        fun followUser(uuid: String?, follow: Boolean)
        fun fetchProfileUser(uuid: String?)
        fun fetchProfilePosts(uuid: String?)
    }

}