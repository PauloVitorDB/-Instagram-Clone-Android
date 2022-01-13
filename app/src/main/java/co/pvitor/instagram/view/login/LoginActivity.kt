package co.pvitor.instagram.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        val textInputEditTextLogin: TextInputEditText = binding.textInputEditTextLogin
        textInputEditTextLogin.addTextChangedListener(watcher)

        val textInputEditTextPassword: TextInputEditText = binding.textInputEditTextPassword
        textInputEditTextPassword.addTextChangedListener(watcher)

        setContentView(binding.root)
    }

    private val watcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.buttonLogin.isEnabled = c.toString().isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

}