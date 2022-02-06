package co.pvitor.instagram.main.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.camera.view.PhotoFragment
import co.pvitor.instagram.common.extensions.replaceFragment
import co.pvitor.instagram.databinding.ActivityMainBinding
import co.pvitor.instagram.home.view.HomeFragment
import co.pvitor.instagram.profile.view.ProfileFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationBarView

class MainActivity: AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null

    private var homeFragment = HomeFragment()
    private var profileFragment = ProfileFragment()
    private var photoFragment = PhotoFragment()

    /*
    * 1º Loading the fragments at the beginning
    * Positive point: Fragments remain alive throughout the app run (Not reloaded using ".replace")
    * Negative point: Fragments start as soon as the app starts, this can cause slowdowns as the resources of each fragment will load
    * */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            title = ""
        }

        currentFragment = homeFragment

        val fragmentId: Int = R.id.main_fragment

        supportFragmentManager.beginTransaction().apply {
            add(fragmentId, profileFragment, "profile").hide(profileFragment)
            add(fragmentId, photoFragment, "photo").hide(photoFragment)
            add(fragmentId, homeFragment, "home")
            commit()
        }

        binding.bottomNavigationView.apply {
            setOnItemSelectedListener(this@MainActivity)
            selectedItemId = R.id.menu_bottom_home
        }

        setContentView(binding.root)
    }

    private fun setScrollToolbar(enabled: Boolean) {

        val toolbarAppBarParams = binding.toolbar.layoutParams as AppBarLayout.LayoutParams
        val appBarLayoutCoordinatorLayout = binding.appBarLayout.layoutParams as CoordinatorLayout.LayoutParams

        if(enabled) {
            toolbarAppBarParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            appBarLayoutCoordinatorLayout.behavior = AppBarLayout.Behavior()
        } else {
            toolbarAppBarParams.scrollFlags = 0
            appBarLayoutCoordinatorLayout.behavior = null
        }

        binding.appBarLayout.layoutParams = appBarLayoutCoordinatorLayout
        binding.toolbar.layoutParams = toolbarAppBarParams
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val selectedFragment: Fragment? = when(item.itemId) {
            R.id.menu_bottom_home -> {
                setScrollToolbar(true)
                homeFragment
            }
            R.id.menu_bottom_add -> {
                setScrollToolbar(false)
                photoFragment
            }
            R.id.menu_bottom_profile -> {
                setScrollToolbar(false)
                profileFragment
            }
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
                    currentFragment?.also {
                        supportFragmentManager.beginTransaction().hide(it).show(selectedFragment).commit()
                    }
                    currentFragment = this
//                    replaceFragment(R.id.main_fragment, selectedFragment)
                }
                true
            }
        }


        return true
    }

}