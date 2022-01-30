package co.pvitor.instagram.register.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.util.CustomTextWatcher
import co.pvitor.instagram.databinding.FragmentRegisterEmailBinding
import co.pvitor.instagram.login.view.LoginActivity
import co.pvitor.instagram.register.RegisterEmail
import co.pvitor.instagram.register.presentation.RegisterEmailPresenter
import co.pvitor.instagram.register.view.RegisterNamePasswordFragment.Companion.KEY_EMAIL

class RegisterEmailFragment: Fragment(), RegisterEmail.View {

    private var _binding: FragmentRegisterEmailBinding? = null
    private val binding get() = _binding!!

    private var _fragmentAttachListener: FragmentAttachListener? = null
    private val fragmentAttachListener get() = _fragmentAttachListener!!

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = RegisterEmailPresenter(this, DependencyInjector.registerRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterEmailBinding.bind(view)

        binding.textInputEditTextRegisterEmail.addTextChangedListener(watcher)

        binding.textViewStepLogin.setOnClickListener(onLogin)

        binding.loadingButtonRegisterEmail.setOnClickListener {
            presenter.registerEmail(binding.textInputEditTextRegisterEmail.text.toString())
        }

    }

    override fun onAttach(context: Context) {
        if(context is RegisterActivity) {
            _fragmentAttachListener = context
        }
        super.onAttach(context)
    }

    private val onLogin = View.OnClickListener {
        activity?.finish()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    private val watcher: TextWatcher = CustomTextWatcher {
        binding.loadingButtonRegisterEmail.isEnabled = it.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _fragmentAttachListener = null
        presenter.onDestroy()
    }

    override fun showProgress(enabled: Boolean) {
        binding.loadingButtonRegisterEmail.showProgress(enabled)
    }

    override fun displayEmailFailure(@StringRes message: Int?) {
        binding.textInputLayoutRegisterEmailInput.error = message?.let { getText(it) }
    }

    override fun nextFragmentStep(email: String) {

        val fragment: Fragment = RegisterNamePasswordFragment()

        fragment.apply {
            arguments = Bundle().apply {
                putString(KEY_EMAIL, email)
            }
        }

        fragmentAttachListener.replaceFragment(fragment)
    }

}

