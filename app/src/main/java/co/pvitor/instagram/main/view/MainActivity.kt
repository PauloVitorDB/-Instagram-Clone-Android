package co.pvitor.instagram.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.camera.view.PhotoFragment
import co.pvitor.instagram.common.extensions.replaceFragment
import co.pvitor.instagram.databinding.ActivityMainBinding
import co.pvitor.instagram.home.view.HomeFragment
import co.pvitor.instagram.profile.view.ProfileFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity: AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null

    private var homeFragment = HomeFragment()
    private var profileFragment = ProfileFragment()
    private var photoFragment = PhotoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            title = ""
        }

        binding.bottomNavigationView.apply {
            setOnItemSelectedListener(this@MainActivity)
            selectedItemId = R.id.menu_bottom_home
        }

        setContentView(binding.root)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val selectedFragment: Fragment? = when(item.itemId) {
            R.id.menu_bottom_home -> homeFragment
            R.id.menu_bottom_add -> photoFragment
            R.id.menu_bottom_profile -> profileFragment
            /*
            R.id.menu_bottom_favorite -> null,
            R.id.menu_bottom_search -> null,
            */
            else -> null
        }

        if(selectedFragment != null) {
            return if(selectedFragment == currentFragment) {
                false
            } else {
                selectedFragment.apply {
                    currentFragment = this
                    replaceFragment(R.id.main_fragment, selectedFragment)
                }
                true
            }
        }


        return true
    }

}