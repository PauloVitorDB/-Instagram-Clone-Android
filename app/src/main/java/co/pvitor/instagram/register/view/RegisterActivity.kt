package co.pvitor.instagram.register.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.common.view.CropperImageFragment
import co.pvitor.instagram.common.view.CropperImageFragment.Companion.KEY_URI
import co.pvitor.instagram.databinding.ActivityRegisterBinding
import co.pvitor.instagram.main.view.MainActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var currentPhotoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        replaceFragment(RegisterEmailFragment())
    }

    override fun replaceFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()

        val isFragmentAttached = supportFragmentManager.findFragmentById(R.id.fragment_register) != null

        if(isFragmentAttached) {
            transaction.apply {
                replace(R.id.fragment_register, fragment)
                addToBackStack(null)
            }
        } else {
            transaction.add(R.id.fragment_register, fragment)
        }

        transaction.commit()
    }

    override fun nextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showCropperImageFragment(uri: Uri) {

        val fragment = CropperImageFragment()

        fragment.apply {
            arguments = Bundle().apply {
                putParcelable(KEY_URI, uri)
            }
        }

        replaceFragment(fragment)
    }

    /*
        https://developer.android.com/training/basics/intents/result
        https://developer.android.com/reference/androidx/activity/result/contract/ActivityResultContracts
     */
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            showCropperImageFragment(it)
        }
    }

    override fun openGallery() {
       getContent.launch("image/*")
    }

    private val getCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { saved ->
        if(saved) {
            showCropperImageFragment(currentPhotoUri)
        }
    }

    @Throws(ActivityNotFoundException::class)
    override fun openCamera() {

        val photoFile: File? = createImageFile()

        photoFile?.also {

            val photoUri: Uri = FileProvider.getUriForFile(
                this,
                "co.pvitor.instagram.fileprovider",
                photoFile
            )

            currentPhotoUri = photoUri

            try {
                getCamera.launch(currentPhotoUri)
            } catch(e: ActivityNotFoundException) {
                Log.e("activityNotFound", e.message.toString())
            }

        }

    }

    @Throws(IOException::class)
    private fun createImageFile() : File? {

        val file: File? = try {
            val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val timestamp = SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()).format(
                Date()
            )
            File.createTempFile("JPEG_${timestamp}_", ".jpg", dir)
        } catch (e: IOException) {
            Log.e("CreateImageFile", e.message.toString())
            null
        }

        return file
    }

}