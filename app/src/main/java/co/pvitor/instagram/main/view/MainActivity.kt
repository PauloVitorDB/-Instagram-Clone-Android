package co.pvitor.instagram.main.view

import android.os.Bundle
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import co.pvitor.instagram.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        setContentView(binding.root)
    }

}