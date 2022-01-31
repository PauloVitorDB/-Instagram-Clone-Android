package co.pvitor.instagram.register.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.view.CropperImageFragment.Companion.CROPPED_URI_KEY
import co.pvitor.instagram.common.view.CropperImageFragment.Companion.KEY_URI
import co.pvitor.instagram.common.view.CustomDialog
import co.pvitor.instagram.databinding.FragmentRegisterPhotoBinding
import co.pvitor.instagram.register.RegisterPhoto
import co.pvitor.instagram.register.presentation.RegisterPhotoPresenter
import com.google.android.material.snackbar.Snackbar

class RegisterPhotoFragment: Fragment(), RegisterPhoto.View {

    private var _binding: FragmentRegisterPhotoBinding? = null
    private val binding get() = _binding!!

    private var _fragmentAttachListener: FragmentAttachListener? = null
    private val fragmentAttachListener get() = _fragmentAttachListener!!

    override lateinit var presenter: RegisterPhoto.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        presenter = RegisterPhotoPresenter(this, DependencyInjector.registerRepository())

        // https://developer.android.com/guide/fragments/communicate?hl=pt-br
        setFragmentResultListener(CROPPED_URI_KEY) { requestKey, bundle ->
            val uri = bundle.getParcelable<Uri>(KEY_URI)
            uri?.let {
                presenter.updateUserPhoto(it)
            }
        }

    }

    private fun replaceUserPhoto(uri: Uri) {

        val bm: Bitmap = if(Build.VERSION.SDK_INT >= 28 ) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        }

        binding.imageViewAddPhoto.setImageBitmap(bm)

    }

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

        binding.apply {

            imageViewAddPhoto.setOnClickListener(addPhotoListener)
            loadingButtonAddPhoto.setOnClickListener(addPhotoListener)

            buttonSkipStep.setOnClickListener {
                fragmentAttachListener.nextActivity()
            }

        }
    }

    override fun onAttach(context: Context) {
        if(context is RegisterActivity) {
            _fragmentAttachListener = context
        }
        super.onAttach(context)
    }

    private val addPhotoListener = View.OnClickListener {

        val dialog = CustomDialog(requireContext())

        dialog.addTextButton(
            R.string.galery,
            R.string.camera
        ) {
            when(it.id) {
                R.string.galery -> fragmentAttachListener.openGallery()
                R.string.camera -> fragmentAttachListener.openCamera()
            }
            dialog.dismiss()
        }
        dialog.setTitle(getText(R.string.add_profile_photo))
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDestroy()
    }

    override fun showProgress(enabled: Boolean) {
        binding.loadingButtonAddPhoto.showProgress(enabled)
    }

    override fun onUpdateError(message: Int?) {
        message?.let { Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show() }
    }

    override fun onUpdateSuccess(uri: Uri) {
        replaceUserPhoto(uri)
        fragmentAttachListener.nextActivity()
    }

}