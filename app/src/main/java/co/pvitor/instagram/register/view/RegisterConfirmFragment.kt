package co.pvitor.instagram.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.FragmentRegisterConfirmBinding

class RegisterConfirmFragment: Fragment() {

    private var _binding: FragmentRegisterConfirmBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}