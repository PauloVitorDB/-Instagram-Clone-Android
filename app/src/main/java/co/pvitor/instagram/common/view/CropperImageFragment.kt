package co.pvitor.instagram.common.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.FragmentImageCropperBinding
import java.io.File

class CropperImageFragment: Fragment(R.layout.fragment_image_cropper) {

    companion object {
        const val KEY_URI = "uri"
        const val CROPPED_URI_KEY = "croppedUriKey"
    }

    private var _binding: FragmentImageCropperBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentImageCropperBinding.bind(view)

        val uri = arguments?.getParcelable<Uri>(KEY_URI)

        with(binding) {

            cropperImageView.apply {

                setAspectRatio(1, 1)
                setFixedAspectRatio(true)
                setImageUriAsync(uri)

                setOnCropImageCompleteListener { view, result ->

                    // https://developer.android.com/guide/fragments/communicate?hl=pt-br
                    setFragmentResult(CROPPED_URI_KEY, bundleOf(
                        KEY_URI to result.uri
                    ))

                    parentFragmentManager.popBackStack()
                }
            }

            buttonCropperCancel.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            buttonCropperSave.setOnClickListener {

                cropperImageView.apply {

                    val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

                    if(dir != null) {

                        val uriToSaveCroppedImage = Uri.fromFile(
                            File(
                                dir.path,
                                "${System.currentTimeMillis()}.jpeg"
                            )
                        )

                        saveCroppedImageAsync(uriToSaveCroppedImage)
                    }

                }

            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}