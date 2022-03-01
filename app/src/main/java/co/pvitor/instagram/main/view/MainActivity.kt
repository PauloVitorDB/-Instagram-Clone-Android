package co.pvitor.instagram.main.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import co.pvitor.instagram.R
import co.pvitor.instagram.add.Add
import co.pvitor.instagram.post.view.AddFragment
import co.pvitor.instagram.common.extensions.replaceFragment
import co.pvitor.instagram.databinding.ActivityMainBinding
import co.pvitor.instagram.home.view.HomeFragment
import co.pvitor.instagram.profile.view.ProfileFragment
import co.pvitor.instagram.search.Search
import co.pvitor.instagram.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationBarView

class MainActivity: AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener,
    Add.ResultListener,
    Search.SearchListener
{

    private lateinit var binding: ActivityMainBinding

    private var currentFragment: Fragment? = null

    private lateinit var homeFragment:  HomeFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var addFragment: AddFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            title = ""
        }

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        profileFragment = ProfileFragment()
        addFragment = AddFragment()

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
            R.id.menu_bottom_profile -> {
                setScrollToolbar(false)
                profileFragment
            }
            R.id.menu_bottom_home -> {
                setScrollToolbar(true)
                homeFragment
            }
            R.id.menu_bottom_search -> {
                setScrollToolbar(false)
                searchFragment
            }
            R.id.menu_bottom_add -> {
                setScrollToolbar(false)
                addFragment
            }
            else -> null
        }

        if(selectedFragment != null) {

            val fragmentTag = selectedFragment.javaClass.simpleName

            return if(currentFragment?.tag.equals(fragmentTag)) {
                false
            } else {
                currentFragment = selectedFragment
                replaceFragment(R.id.main_fragment, selectedFragment, fragmentTag)
                true
            }

        }

        return true
    }

    override fun onPostCreated() {

        homeFragment.presenter.clear()

        val fragmentHomeHasInitialized: Boolean = supportFragmentManager.findFragmentByTag(profileFragment.javaClass.simpleName) != null
        if(fragmentHomeHasInitialized) {
            profileFragment.presenter.clear()
        }

        binding.bottomNavigationView.selectedItemId = R.id.menu_bottom_home
    }


    override fun toProfile(uuid: String) {
        val fragment = ProfileFragment()
        fragment.apply {
            arguments = Bundle().apply {
                putString(ProfileFragment.KEY_USER_ID, uuid )
            }
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment, fragment.javaClass.simpleName + "detail")
            addToBackStack(null)
            commit()
        }
    }
}