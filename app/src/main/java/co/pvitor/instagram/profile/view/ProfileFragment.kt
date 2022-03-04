package co.pvitor.instagram.profile.view

import android.util.Log
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.BaseFragment
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.FragmentProfileBinding
import co.pvitor.instagram.profile.Profile
import co.pvitor.instagram.profile.presentation.ProfilePresenter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class ProfileFragment: BaseFragment<Profile.Presenter, FragmentProfileBinding>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View {

    companion object {
        const val KEY_USER_ID = "uuid"
    }

    private var uuid: String? = null

    override lateinit var presenter: Profile.Presenter

    private lateinit var modalBottomSheetDialog: ModalBottomSheetDialog

    private val rvPostAdapter = PostGridAdapter()

    override fun setupPresenter() {
        presenter = ProfilePresenter(this, DependencyInjector.profileRepository())
    }

    override fun setupOnViewCreated() {

        uuid = arguments?.getString(KEY_USER_ID)

        binding.recyclerViewPhotoGrid.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = rvPostAdapter
        }

        modalBottomSheetDialog = ModalBottomSheetDialog()
        modalBottomSheetDialog.addItems(
            BottomSheetItem(
                R.string.config,
                R.drawable.ic_baseline_settings_24
            )
        ) {

            when(it.id) {
                R.string.config -> Log.d("BottomSheet", "config")
            }

            modalBottomSheetDialog.dismiss()
        }

        val tabLayout: TabLayout = binding.tabLayoutProfileGrid

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabId = tab?.id
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabId = tab?.id
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val tabId = tab?.id
            }

        })

        binding.buttonEditProfile.setOnClickListener {
            if(it.tag == true) {
                binding.buttonEditProfile.text = getText(R.string.follow)
                presenter.followUser(uuid, false)
            } else {
                binding.buttonEditProfile.text = getText(R.string.unfollow)
                presenter.followUser(uuid, true)
            }
        }

        presenter.fetchProfileUser(uuid)
    }

    override fun getMenu(): Int {
        return R.menu.menu_toolbar_profile
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_item_config -> {
                modalBottomSheetDialog.show(childFragmentManager, "")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun displayRequestFailure(message: Int?) {
        message?.let {
            Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun displayUserProfile(user:  Pair<UserAuth, Boolean?>) {

        val (userAuth, following) = user

        userAuth.apply {
            binding.textViewProfileUsername.text = name.toString()
            binding.textViewPostsCounter.text = countPosts.toString()
            binding.textViewFollowersCounter.text = countFollowers.toString()
            binding.textViewFollowingCounter.text = countFollowing.toString()

            binding.buttonEditProfile.text = when(following) {
                null -> getString(R.string.edit_profile)
                true -> getString(R.string.unfollow)
                false -> getString(R.string.follow)
            }

            binding.buttonEditProfile.tag = following

        }

        presenter.fetchProfilePosts(uuid)
    }

    override fun displayUserPosts(posts: List<Post>) {
        rvPostAdapter.posts = posts
        rvPostAdapter.notifyDataSetChanged()
        binding.textViewEmptyUserPosts.visibility = View.GONE
        binding.recyclerViewPhotoGrid.visibility = View.VISIBLE
    }

    override fun displayEmptyPosts() {
        binding.recyclerViewPhotoGrid.visibility = View.GONE
        binding.textViewEmptyUserPosts.visibility = View.VISIBLE
    }

    override fun showProgress(enabled: Boolean) {
        binding.progressBarHome.visibility = if(enabled) View.VISIBLE else View.GONE
    }
}



