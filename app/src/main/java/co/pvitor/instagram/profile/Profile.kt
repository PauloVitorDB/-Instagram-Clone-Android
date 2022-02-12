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
        fun displayUserProfile(user: UserAuth)
        fun displayRequestFailure(@StringRes message: Int?)
    }

    interface Presenter: StatefulPresenter<State> {

    }

    interface StatefulPresenter<S: State>: BasePresenter {
        fun getState(): S
        fun subscribe(state: S?)
    }

    interface State {
        fun fetchProfileUser(): UserAuth?
        fun fetchProfilePosts(): List<Post>?
    }
    
}