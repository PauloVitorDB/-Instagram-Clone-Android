package co.pvitor.instagram.post.view

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import co.pvitor.instagram.R
import co.pvitor.instagram.common.base.BaseFragment
import co.pvitor.instagram.common.base.DependencyInjector
import co.pvitor.instagram.databinding.FragmentGalleryBinding
import co.pvitor.instagram.post.Post
import co.pvitor.instagram.post.presentation.PostPresenter
import co.pvitor.instagram.post.view.AddFragment.Companion.URI_KEY
import co.pvitor.instagram.post.view.AddFragment.Companion.URI_SAVED_KEY
import com.google.android.material.snackbar.Snackbar

class GalleryFragment : BaseFragment<Post.Presenter, FragmentGalleryBinding>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    private lateinit var galleryAdapter: GalleryAdapter

    override fun setupPresenter() {
        presenter = PostPresenter(this, DependencyInjector.postRepository(requireContext()))
    }

    override fun getMenu(): Int {
        return R.menu.menu_add_share
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {

        if(item.itemId == R.id.share_post) {
            setFragmentResult(URI_SAVED_KEY, bundleOf(
                URI_KEY to presenter.selectedUri
            ))
        }

        return true
    }

    override fun setupOnViewCreated() {

        galleryAdapter = GalleryAdapter(this::changeSelectedGalleryImage)
        binding.recyclerViewGallery.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        presenter.fetchPictures()
    }

    override fun showProgress(enabled: Boolean) {
        binding.progressBarGallery.visibility = if(enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: Int?) {
        message?.let {
            Snackbar.make(binding.root, getText(it), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun displayEmptyPictures() {
        binding.apply {
            recyclerViewGallery.visibility = View.GONE
            textViewEmptyGallery.visibility = View.VISIBLE
        }
    }

    override fun displayPictures(pictureList: List<Uri>) {
        binding.apply {

            textViewEmptyGallery.visibility = View.GONE
            recyclerViewGallery.visibility = View.VISIBLE

            galleryAdapter.deviceGalleryPictureList = pictureList
            galleryAdapter.notifyDataSetChanged()

            changeSelectedGalleryImage(pictureList.first())
        }
    }

    private fun changeSelectedGalleryImage(imageUri: Uri) {
        binding.imageViewGalleryImage.setImageURI(imageUri)
        binding.nestedScrollViewGallery.smoothScrollTo(0, 0)
        presenter.selectedUri = imageUri
    }

    override lateinit var presenter:  Post.Presenter

}