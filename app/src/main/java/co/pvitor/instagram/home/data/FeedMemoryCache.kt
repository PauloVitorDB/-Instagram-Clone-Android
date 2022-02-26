package co.pvitor.instagram.home.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.Post

object FeedMemoryCache: LocalCache<List<Post>> {

    private var postList: List<Post>? = null

    override fun isCached(): Boolean {
        return postList != null
    }

    override fun set(data: List<Post>?) {
        postList = data
    }

    override fun get(key: String?): List<Post>? {
        return postList
    }

}