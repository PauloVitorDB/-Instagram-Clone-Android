package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.Post

object PostsMemoryCache: LocalCache<List<Post>> {

    private var postsList: List<Post>? = null

    override fun get(key: String?) : List<Post>? {
        return postsList
    }

    override fun isCached(): Boolean {
        return postsList != null
    }

    override fun set(data: List<Post>?) {
        postsList = data
    }
}