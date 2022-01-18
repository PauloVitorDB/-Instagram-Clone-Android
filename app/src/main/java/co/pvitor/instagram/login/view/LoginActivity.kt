package co.pvitor.instagram.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import co.pvitor.instagram.common.view.LoadingButton
import co.pvitor.instagram.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loadingButton: LoadingButton
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        val textInputEditTextLogin: TextInputEditText = binding.textInputEditTextLogin
        textInputEditTextLogin.addTextChangedListener(watcher)

        val textInputEditTextPassword: TextInputEditText = binding.textInputEditTextPassword
        textInputEditTextPassword.addTextChangedListener(watcher)

        loadingButton = binding.loadingButtonLogin
        loadingButton.setOnClickListener {
            loadingButton.showProgress(true)

            Handler(Looper.getMainLooper()).postDelayed({
                textInputEditTextLogin.error = "Inválid User"
                loadingButton.showProgress(false)
            }, 3000)
        }

        setContentView(binding.root)
    }

    private val watcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
            loadingButton.isEnabled = c.toString().isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

}