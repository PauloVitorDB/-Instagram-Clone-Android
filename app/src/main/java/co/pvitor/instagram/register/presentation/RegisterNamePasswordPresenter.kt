package co.pvitor.instagram.register.presentation

import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.register.RegisterNamePassword
import co.pvitor.instagram.register.data.RegisterCallback
import co.pvitor.instagram.register.data.RegisterRepository

class RegisterNamePasswordPresenter(
    private var _view: RegisterNamePassword.View?,
    private val repository: RegisterRepository
): RegisterNamePassword.Presenter {

    private val view get() = _view!!

    override fun createUser(email: String, password: String, name: String) {

        val isPasswordValid = password.length >= 8
        if(!isPasswordValid) {
            view.displayPasswordError(R.string.invalid_password_length)
        } else {
            view.displayPasswordError(null)
        }

        val isNameValid = name.length > 3
        if(!isNameValid) {
            view.displayNameError(R.string.invalid_name_length)
        }

        if(isNameValid && isPasswordValid) {

            view.showProgress(true)

            repository.register(email, password, name, object: RegisterCallback<UserAuth> {

                override fun onSuccess(response: UserAuth) {

                }

                override fun onFailure(message: Int?) {
                    view.displayRegisterError(message)
                }

                override fun onComplete() {
                    view.showProgress(false)
                }

            })
        }

    }

    override fun onDestroy() {
        _view = null
    }

}
