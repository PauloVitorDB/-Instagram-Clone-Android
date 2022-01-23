package co.pvitor.instagram.register.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.common.view.CustomDialog
import co.pvitor.instagram.databinding.FragmentRegisterPhotoBinding

class RegisterPhotoFragment: Fragment() {

    private var _binding: FragmentRegisterPhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        return inflater.inflate(R.layout.fragment_register_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterPhotoBinding.bind(view)

        dialog = CustomDialog(requireContext())

        binding.imageViewAddPhoto.setOnClickListener(addPhotoListener)
        binding.loadingButtonAddPhoto.setOnClickListener(addPhotoListener)
    }

    private val addPhotoListener = View.OnClickListener {

        dialog.addTextButton(
            R.string.galery,
            R.string.camera
        ) {
            when(it.id) {
                R.string.galery ->  Log.d("buttonListener", "Galeria")
                R.string.camera -> Log.d("buttonListener", "Cameria")
            }
            dialog.dismiss()
        }
        dialog.setTitle(getText(R.string.add_profile_photo))
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}