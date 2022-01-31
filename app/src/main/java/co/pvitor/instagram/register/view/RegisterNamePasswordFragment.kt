package co.pvitor.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.extensions.hideKeyboard
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.CustomTextWatcher
import co.pvitor.instagram.databinding.FragmentRegisterNamePasswordBinding
import co.pvitor.instagram.register.RegisterNamePassword
import co.pvitor.instagram.register.presentation.RegisterNamePasswordPresenter
import com.google.android.material.snackbar.Snackbar

class RegisterNamePasswordFragment: Fragment(), RegisterNamePassword.View {

    companion object {
        const val KEY_EMAIL = "email"
        const val KEY_NAME = "name"
    }

    private var _binding: FragmentRegisterNamePasswordBinding? = null
    private val binding get() = _binding!!

    override lateinit var presenter: RegisterNamePassword.Presenter

    private var _fragmentAttachListener: FragmentAttachListener? = null
    private val fragmentAttachListener get() = _fragmentAttachListener!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = RegisterNamePasswordPresenter(this, DependencyInjector.registerRepository())
    }

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
                    activity?.hideKeyboard()
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

    override fun onAttach(context: Context) {
        if(context is RegisterActivity) {
            _fragmentAttachListener = context
        }
        super.onAttach(context)
    }

    override fun onDestroy() {
        _binding = null
        _fragmentAttachListener = null
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
        fragmentAttachListener.replaceFragment(fragment)
    }

}