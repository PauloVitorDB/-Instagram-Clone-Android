package co.pvitor.instagram.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import co.pvitor.instagram.common.util.CustomTextWatcher
import co.pvitor.instagram.common.view.LoadingButton
import co.pvitor.instagram.databinding.ActivityLoginBinding
import co.pvitor.instagram.login.Login
import co.pvitor.instagram.login.data.FakeDataSource
import co.pvitor.instagram.login.data.LoginRepository
import co.pvitor.instagram.login.presentation.LoginPresenter
import co.pvitor.instagram.main.view.MainActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loadingButton: LoadingButton
    override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val dataSource = FakeDataSource()
        presenter = LoginPresenter(this, LoginRepository(dataSource))

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.textInputEditTextLogin.addTextChangedListener(watcher)
        binding.textInputEditTextPassword.addTextChangedListener(watcher)

        loadingButton = binding.loadingButtonLogin
        loadingButton.setOnClickListener {

            presenter.login(
                binding.textInputEditTextLogin.text.toString(),
                binding.textInputEditTextPassword.text.toString()
            )

        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private val watcher = CustomTextWatcher() {
        binding.loadingButtonLogin.isEnabled = (
                binding.textInputEditTextLogin.text.toString().isNotEmpty() &&
                binding.textInputEditTextPassword.text.toString().isNotEmpty()
        )
    }

    override fun showProgress(enabled: Boolean) {
        loadingButton.showProgress(enabled)
    }

    override fun displayEmailFailure(message: Int?) {
        binding.textInputLayoutLoginInput.error = message?.let { getText(it) }
    }

    override fun displayPasswordFailure(message: Int?) {
        binding.textInputLayoutPasswordInput.error = message?.let { getText(it) }
    }

    override fun onUserAuthenticate() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onUserUnauthorized(@StringRes message: Int) {
        Snackbar.make(binding.root, getText(message), Snackbar.LENGTH_LONG).show()
    }

}