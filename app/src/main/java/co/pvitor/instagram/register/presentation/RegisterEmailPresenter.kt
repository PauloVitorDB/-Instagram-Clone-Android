package co.pvitor.instagram.register.presentation

import android.util.Patterns
import co.pvitor.instagram.R
import co.pvitor.instagram.register.RegisterEmail
import co.pvitor.instagram.register.data.RegisterCallback
import co.pvitor.instagram.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var _view: RegisterEmail.View?,
    private val repository: RegisterRepository
): RegisterEmail.Presenter {

    private val view get() = _view!!

    override fun registerEmail(email: String) {

        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        view.displayEmailFailure(null)

        if(isEmailValid) {

            view.showProgress(true)

            repository.register(email, object: RegisterCallback<String> {

                override fun onComplete() {
                    view.showProgress(false)
                }

                override fun onFailure(message: Int?) {
                    view.displayEmailFailure(message)
                }

                override fun onSuccess(response: String) {
                    view.nextFragmentStep(response)
                }

            })
        } else {
            view.displayEmailFailure(R.string.invalid_email)
        }

    }

    override fun onDestroy() {
        _view = null
    }
}