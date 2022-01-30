package co.pvitor.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.FragmentRegisterConfirmBinding
import co.pvitor.instagram.register.view.RegisterNamePasswordFragment.Companion.KEY_NAME

class RegisterConfirmFragment: Fragment() {

    private var _binding: FragmentRegisterConfirmBinding? = null
    private val binding get() = _binding!!

    private var _fragmentAttachListener: FragmentAttachListener? = null
    private val fragmentAttachListener get() = _fragmentAttachListener!!

    override fun onAttach(context: Context) {
        if(context is RegisterActivity) {
            _fragmentAttachListener = context
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterConfirmBinding.bind(view)

        binding.apply {

            arguments?.let {
                textViewConfirmRegistration.text = getString(
                    R.string.confirm_registration, it.getString(KEY_NAME).toString()
                )
            }

            loadingButtonRegisterConfirm.setOnClickListener {
                val fragment = RegisterPhotoFragment()
                fragmentAttachListener.replaceFragment(fragment)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}