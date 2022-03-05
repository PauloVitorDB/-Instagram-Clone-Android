package co.pvitor.instagram.search.view

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.BaseFragment
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.databinding.FragmentSearchBinding
import co.pvitor.instagram.search.Search
import co.pvitor.instagram.search.presentation.SearchPresenter
import com.google.android.material.snackbar.Snackbar

class SearchFragment: BaseFragment<Search.Presenter, FragmentSearchBinding>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
), Search.View {

    override lateinit var presenter: Search.Presenter

    private val searchAdapter by lazy {  SearchAdapter(onItemClicked ) }

    private var searchListener: Search.SearchListener? = null

    override fun onAttach(context: Context) {
        if(context is Search.SearchListener) {
            searchListener = context
        }
        super.onAttach(context)
    }

    override fun setupPresenter() {
        presenter = SearchPresenter(this, DependencyInjector.searchRepository())
    }

    override fun setupOnViewCreated() {

        binding.apply {

            recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerViewSearch.adapter = searchAdapter

        }

    }

    private val onItemClicked: (String) -> Unit = { uuid: String ->
        searchListener?.toProfile(uuid)
    }

    override fun getMenu(): Int = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)  {

        super.onCreateOptionsMenu(menu, inflater)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        presenter.fetchUsers(it)
                    }
                    return true
                }

            })
        }

    }

    override fun displayEmptyUsersList() {
        binding.recyclerViewSearch.visibility = View.GONE
        binding.textViewSearchNotFound.visibility = View.VISIBLE
    }

    override fun displayUsersList(users: List<User>) {
        binding.textViewSearchNotFound.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.VISIBLE
        searchAdapter.userList = users
        searchAdapter.notifyDataSetChanged()
    }

    override fun displayRequestFailure(message: Int?) {
        message?.let { Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show() }
    }

    override fun showProgress(enabled: Boolean) {
        binding.progressBarSearch.visibility = if(enabled) View.VISIBLE else View.GONE
    }


}