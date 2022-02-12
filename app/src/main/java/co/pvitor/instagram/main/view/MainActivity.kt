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

    private lateinit var fragmentSavedState: HashMap<String, Fragment.SavedState?>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            title = ""
        }

        if (savedInstanceState == null) {
            fragmentSavedState = HashMap()
        } else {
           savedInstanceState.getSerializable("fragmentState") as HashMap<String, Fragment.SavedState?>
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

    // store in savedInstanceState
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("fragmentState", fragmentSavedState)
        super.onSaveInstanceState(outState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val selectedFragment: Fragment? = when(item.itemId) {
            R.id.menu_bottom_profile -> {
                ProfileFragment()
            }
            R.id.menu_bottom_home -> {
                HomeFragment()
            }
            R.id.menu_bottom_add -> {
                PhotoFragment()
            }
            else -> null
        }

        val fragmentAttached: Fragment? = supportFragmentManager.findFragmentById(R.id.main_fragment)

        val selectedFragmentTag = selectedFragment?.javaClass?.simpleName
        val isCurrentFragmentTagSelected = fragmentAttached?.tag.equals(selectedFragmentTag)

        if(!isCurrentFragmentTagSelected) {
            fragmentAttached?.let {
                fragmentSavedState.put(
                    it.tag!!,
                    supportFragmentManager.saveFragmentInstanceState(it)
                )
            }
        }

        selectedFragment?.setInitialSavedState(fragmentSavedState[selectedFragmentTag])
        selectedFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, it, selectedFragmentTag)
                .addToBackStack(null)
                .commit()
        }

        return true
    }

}