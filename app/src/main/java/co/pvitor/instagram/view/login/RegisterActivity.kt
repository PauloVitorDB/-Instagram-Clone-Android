package co.pvitor.instagram.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}