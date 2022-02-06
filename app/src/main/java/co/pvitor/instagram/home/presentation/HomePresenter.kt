package co.pvitor.instagram.home.presentation

import co.pvitor.instagram.home.Home

class HomePresenter(
    private var _view: Home.View?
): Home.Presenter {

    private val view get() = _view!!

    override fun onDestroy() {
        _view = null
    }

    override fun fetchHomePosts() {

    }

}