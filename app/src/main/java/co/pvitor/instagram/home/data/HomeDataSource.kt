package co.pvitor.instagram.home.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.User
import co.pvitor.instagram.common.util.RequestCallback

interface HomeDataSource {

    fun fetchPosts(uuid: String, callback: RequestCallback<List<Post>>)
    fun logout() { throw UnsupportedOperationException() }

    fun fetchSession(): User { throw UnsupportedOperationException() }
    fun putFeedList(data: List<Post>?) { throw UnsupportedOperationException() }
}