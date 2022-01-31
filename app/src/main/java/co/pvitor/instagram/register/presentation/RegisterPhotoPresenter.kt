package co.pvitor.instagram.register.presentation

import android.net.Uri
import co.pvitor.instagram.register.RegisterPhoto
import co.pvitor.instagram.register.data.RegisterCallback
import co.pvitor.instagram.register.data.RegisterRepository

class RegisterPhotoPresenter(
    private var _view: RegisterPhoto.View?,
    private val repository: RegisterRepository
): RegisterPhoto.Presenter {

    private val view get() = _view!!

    override fun updateUserPhoto(uri: Uri) {

        view.showProgress(true)

        repository.updateUserPhoto(uri, object: RegisterCallback<Uri> {

            override fun onComplete() {
                view.showProgress(false)
            }

            override fun onFailure(message: Int?) {
                view.onUpdateError(message)
            }

            override fun onSuccess(response: Uri) {
                view.onUpdateSuccess(response)
            }

        })
    }

    override fun onDestroy() {
        _view = null
    }

}