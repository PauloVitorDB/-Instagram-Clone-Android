package co.pvitor.instagram.home.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.BaseFragment
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.FragmentHomeBinding
import co.pvitor.instagram.home.Home
import co.pvitor.instagram.home.presentation.HomePresenter
import com.google.android.material.snackbar.Snackbar

class HomeFragment: BaseFragment<Home.Presenter, FragmentHomeBinding>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    override lateinit var presenter: Home.Presenter

    private lateinit var modalBottomSheetDialog: ModalBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun setupPresenter() {
        presenter = HomePresenter(this)
    }

    override fun setupOnViewCreated() {

        modalBottomSheetDialog = ModalBottomSheetDialog()
        modalBottomSheetDialog.addItems(
            BottomSheetItem(
                R.string.stop_following,
                null
            )
        ) {
            when(it.id) {
                R.string.stop_following -> Log.d("BottomSheetHome", "Stop")
            }
            modalBottomSheetDialog.dismiss()
        }

        val recyclerViewPosts = binding.recyclerViewPosts
        recyclerViewPosts.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerViewPosts.adapter = PostAdapter(this, modalBottomSheetDialog)

    }

    @MenuRes
    override fun getMenu(): Int {
        return R.menu.menu_bottom_navigation
    }

    override fun showProgress(enabled: Boolean) {

    }

    override fun displayPosts(posts: List<Post>) {

    }

    override fun displayRequestFailure(@StringRes message: Int?) {
        message?.let {
            Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show()
        }
    }

}

