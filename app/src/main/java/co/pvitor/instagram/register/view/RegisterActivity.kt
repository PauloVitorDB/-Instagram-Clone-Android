package co.pvitor.instagram.register.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), FragmentAttachListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_register, RegisterEmailFragment())
            commit()
        }
    }

    override fun nextStep(fragment: Fragment, email: String) {

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

}