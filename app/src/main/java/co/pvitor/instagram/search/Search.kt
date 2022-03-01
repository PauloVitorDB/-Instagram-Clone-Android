package co.pvitor.instagram.search

import androidx.annotation.StringRes
import co.pvitor.instagram.common.base.BasePresenter
import co.pvitor.instagram.common.base.BaseView
import co.pvitor.instagram.common.model.UserAuth

interface Search {

    interface Presenter: BasePresenter {
        fun fetchUsers(search: String)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestFailure(@StringRes message: Int?)
        fun displayUsersList(users: List<UserAuth>)
        fun displayEmptyUsersList()
    }

    interface SearchListener {
        fun toProfile(uuid: String)
    }
}