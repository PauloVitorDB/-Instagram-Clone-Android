package co.pvitor.instagram.search.presentation

import android.content.Context
import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback
import co.pvitor.instagram.search.Search
import co.pvitor.instagram.search.data.SearchRepository

class SearchPresenter(
    private var _view: Search.View?,
    private val repository: SearchRepository
): Search.Presenter {

    private val view get() = _view!!

    override fun fetchUsers(search: String) {

        view.showProgress(true)

        repository.fetchUsers(search, object: RequestCallback<List<UserAuth>> {
            override fun onSuccess(response: List<UserAuth>) {
                if(response.isNotEmpty()) {
                    view.displayUsersList(response)
                } else {
                    view.displayEmptyUsersList()
                }
            }

            override fun onFailure() {
                view.displayRequestFailure(R.string.error_search)
            }

            override fun onComplete() {
                view.showProgress(false)
            }

        })

    }

    override fun onDestroy() {
        _view = null
    }

}