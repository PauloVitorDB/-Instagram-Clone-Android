package co.pvitor.instagram.add.presentation

import android.net.Uri
import co.pvitor.instagram.add.Add
import co.pvitor.instagram.add.data.AddRepository
import co.pvitor.instagram.common.util.RequestCallback

class AddPresenter(
    private var _view: Add.View?,
    private val repository: AddRepository
): Add.Presenter {

    private val view get() = _view!!

    override fun createPost(uri: Uri, caption: String) {

        repository.createPost(uri, caption, object : RequestCallback<Boolean> {
            override fun onSuccess(response: Boolean) {
                view.displayRequestSuccess(null)
            }

            override fun onFailure() {
                view.displayRequestFailure(null)
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