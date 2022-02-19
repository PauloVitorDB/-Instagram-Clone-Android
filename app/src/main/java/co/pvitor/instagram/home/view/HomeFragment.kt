package co.pvitor.instagram.home.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.BaseFragment
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.view.BottomSheetItem
import co.pvitor.instagram.common.view.ModalBottomSheetDialog
import co.pvitor.instagram.databinding.FragmentHomeBinding
import co.pvitor.instagram.home.Home
import co.pvitor.instagram.home.presentation.HomePresenter
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment<Home.Presenter, FragmentHomeBinding>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    private lateinit var rvPostAdapter: FeedAdapter
    private lateinit var modalBottomSheetDialog: ModalBottomSheetDialog

    override lateinit var presenter: Home.Presenter

    override fun getMenu(): Int {
        return R.menu.menu_home
    }

    override fun setupPresenter() {
        presenter = HomePresenter(this, DependencyInjector.homeRepository())
    }

    override fun setupOnViewCreated() {

        rvPostAdapter = FeedAdapter(requireContext())

        binding.recyclerViewPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = rvPostAdapter
        }

        presenter.displayPosts()
    }

    override fun displayEmptyFeed() {
        binding.recyclerViewPosts.visibility = View.GONE
        binding.textViewEmptyFeed.visibility = View.VISIBLE
    }

    override fun displayFeed(feedList: List<Post>) {
        binding.recyclerViewPosts.visibility = View.VISIBLE
        binding.textViewEmptyFeed.visibility = View.GONE
        rvPostAdapter.postList = feedList
        rvPostAdapter.notifyDataSetChanged()
    }

    override fun displayError(message: Int?) {
        message?.let { Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show() }
    }

    override fun showProgress(enabled: Boolean) {
        binding.progressBarFeed.visibility = if(enabled) View.VISIBLE else View.GONE
    }

}

