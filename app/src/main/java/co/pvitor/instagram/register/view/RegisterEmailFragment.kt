package co.pvitor.instagram.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.FragmentRegisterEmailBinding

class RegisterEmailFragment: Fragment() {

    private var _binding: FragmentRegisterEmailBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}

