package co.pvitor.instagram.home.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.BaseFragment
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.databinding.FragmentHomeBinding
import co.pvitor.instagram.home.Home
import co.pvitor.instagram.home.presentation.HomePresenter
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment<Home.Presenter, FragmentHomeBinding>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    private var rvPostAdapter = PostAdapter()

    override lateinit var presenter: Home.Presenter

    override fun setupPresenter() {
        presenter = HomePresenter(this, DependencyInjector.homeRepository())
    }

    override fun setupOnViewCreated() {

        binding.recyclerViewPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = rvPostAdapter
        }

        presenter.displayPosts()
    }

    override fun displayEmptyFeed() {

    }

    override fun displayFeed(feedList: List<Post>) {
        rvPostAdapter.postList = feedList
        rvPostAdapter.notifyDataSetChanged()
    }

    override fun displayError(message: Int?) {
        message?.let { Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show() }
    }

    override fun showProgress(enabled: Boolean) {

    }

}

