package co.pvitor.instagram.post.presentation

import android.net.Uri
import co.pvitor.instagram.R
import co.pvitor.instagram.post.Post
import co.pvitor.instagram.post.data.PostRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostPresenter(
    private var _view: Post.View?,
    private val repository: PostRepository
): Post.Presenter, CoroutineScope {

    private val view get() = _view!!

    override lateinit var selectedUri: Uri

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    override fun fetchPictures() {

        view.showProgress(true)

        launch {

            val pictureList = repository.fetchPictures()

            withContext(Dispatchers.Main) {
                if (pictureList.isNotEmpty()) {
                    view.displayPictures(pictureList)
                } else {
                    view.displayEmptyPictures()
                }

                view.showProgress(false)
            }

        }

    }

    override fun onDestroy() {
        job.cancel()
        _view = null
    }

}