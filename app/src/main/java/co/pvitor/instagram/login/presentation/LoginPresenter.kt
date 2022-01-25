package co.pvitor.instagram.login.presentation

import android.util.Patterns
import androidx.annotation.StringRes
import co.pvitor.instagram.R
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.login.Login
import co.pvitor.instagram.login.data.LoginCallback
import co.pvitor.instagram.login.data.LoginRepository

class LoginPresenter(
    private var _view: Login.View?,
    private val repository: LoginRepository?
): Login.Presenter {

    private val view get() = _view!!

    override fun login(email: String, password: String) {

        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if(!isEmailValid) {
            view.displayEmailFailure(R.string.invalid_email)
        } else {
            view.displayEmailFailure(null)
        }

        val isPasswordValid = password.length >= 8
        if(!isPasswordValid) {
            view.displayPasswordFailure(R.string.invalid_password_length)
        } else {
            view.displayPasswordFailure(null)
        }

        if(isPasswordValid && isEmailValid) {

            view.showProgress(true)

            repository?.login(email, password, object : LoginCallback<UserAuth> {
                override fun onSuccess(response: UserAuth) {
                    view.onUserAuthenticate()
                }

                override fun onFailure(@StringRes message: Int?) {
                    view.onUserUnauthorized(message)
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