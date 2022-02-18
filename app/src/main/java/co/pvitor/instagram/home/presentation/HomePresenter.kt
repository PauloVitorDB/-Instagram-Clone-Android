package co.pvitor.instagram.home.presentation

import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.util.RequestCallback
import co.pvitor.instagram.home.Home
import co.pvitor.instagram.home.data.HomeRepository

class HomePresenter(
    private var _view: Home.View?,
    private val repository: HomeRepository
): Home.Presenter {

    private val view get() = _view!!

    override fun displayPosts() {

        view.showProgress(true)

        repository.fetchPosts(object : RequestCallback<List<Post>> {

            override fun onSuccess(response: List<Post>) {
                if(response.isEmpty()) {
                    view.displayEmptyFeed()
                } else {
                    view.displayFeed(response)
                }
            }

            override fun onFailure() {
                view.displayError(R.string.error_feed)
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