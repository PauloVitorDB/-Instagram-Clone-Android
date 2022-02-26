package co.pvitor.instagram.post.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import co.pvitor.instagram.R
import co.pvitor.instagram.post.view.AddFragment.Companion.START_CAMERA_KEY
import co.pvitor.instagram.post.view.AddFragment.Companion.CAMERA_KEY
import co.pvitor.instagram.post.view.AddFragment.Companion.URI_KEY
import co.pvitor.instagram.post.view.AddFragment.Companion.URI_SAVED_KEY
import co.pvitor.instagram.common.util.Files
import co.pvitor.instagram.databinding.FragmentCameraBinding

class CameraFragment: Fragment(R.layout.fragment_camera) {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null

    private lateinit var previewViewCamera: PreviewView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCameraBinding.bind(view)

        previewViewCamera = binding.previewViewCamera

        setFragmentResultListener(CAMERA_KEY) { cameraKey, bundle ->

            val shouldStart = bundle.getBoolean(START_CAMERA_KEY)

            if(shouldStart) {
                startCamera()
            }
        }


        binding.buttonCamera.setOnClickListener(takePhoto)
    }

    private val takePhoto = View.OnClickListener {

        // Must be a reference of an object instanced and already bound in "cameraProvider.bindToLifecycle"
        imageCapture?.let {

            val photoFile = Files.generateFile(requireActivity())

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            it.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()), object: ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    val savedUri = Uri.fromFile(photoFile)

                    setFragmentResult(URI_SAVED_KEY, bundleOf(
                        URI_KEY to savedUri
                    ))

                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("SaveCaptureImageFail", exception.message.toString())
                }

            })

        }
    }

    private fun startCamera() {

        // ProcessCameraProvider helps us to link the camera lifecycle with the activity, when it is destroyed the camera will also be
        val processCameraProvider = ProcessCameraProvider.getInstance(requireContext())

        processCameraProvider.addListener({

            val cameraProvider: ProcessCameraProvider = processCameraProvider.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewViewCamera.surfaceProvider)
                }

            // Object instance that will allow us to store the image in a URI by callback, using the "takePicture" function
            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch(e: Exception) {
                Log.d("cameraInitializeFail", e.message.toString())
            }

        }, ContextCompat.getMainExecutor(requireContext()))

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}