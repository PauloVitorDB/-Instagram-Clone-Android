package co.pvitor.instagram.register.view

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.common.util.CustomTextWatcher
import co.pvitor.instagram.databinding.FragmentRegisterNamePasswordBinding
import co.pvitor.instagram.register.RegisterNamePassword
import co.pvitor.instagram.register.presentation.RegisterNamePasswordPresenter
import com.google.android.material.snackbar.Snackbar

class RegisterNamePasswordFragment: Fragment(), RegisterNamePassword.View {

    companion object {
        const val KEY_EMAIL = "email"
    }

    private var _binding: FragmentRegisterNamePasswordBinding? = null
    private val binding get() = _binding!!

    override lateinit var presenter: RegisterNamePassword.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register_name_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterNamePasswordBinding.bind(view)

        val email: String = arguments?.getString(KEY_EMAIL) ?: throw IllegalArgumentException("E-mail is null")

        binding.apply {

            with(this) {

                textInputEditTextName.addTextChangedListener(watcher)
                textInputEditTextRegisterPassword.addTextChangedListener(watcher)

                loadingButtonRegisterPassword.setOnClickListener {
                    presenter.createUser(
                        email,
                        textInputEditTextRegisterPassword.text.toString(),
                        textInputEditTextName.text.toString()
                    )
                }
            }

        }

    }

    private val watcher: TextWatcher = CustomTextWatcher {
        binding.loadingButtonRegisterPassword.isEnabled = (
            binding.textInputEditTextName.text.toString().isNotEmpty()
            && binding.textInputEditTextRegisterPassword.text.toString().isNotEmpty()
        )
    }

    override fun onDestroy() {
        _binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun displayRegisterError(message: Int?) {
        message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun displayNameError(message: Int?) {
        binding.textInputLayoutNameInput.error = message?.let { getText(it) }
    }

    override fun displayPasswordError(message: Int?) {
        binding.textInputLayoutRegisterPasswordInput.error = message?.let { getText(it) }
    }

    override fun showProgress(enabled: Boolean) {
        binding.loadingButtonRegisterPassword.showProgress(enabled)
    }

    override fun nextStep(fragment: Fragment) {

    }

}