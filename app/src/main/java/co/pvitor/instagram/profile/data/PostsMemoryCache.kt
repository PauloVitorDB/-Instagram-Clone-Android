package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Post

object PostsMemoryCache: ProfileCache<List<Post>> {

    private var postsList: List<Post>? = null

    override fun get(key: String) : List<Post>? {
        return postsList
    }

    override fun isCached(): Boolean {
        return postsList != null
    }

    override fun put(data: List<Post>) {
        postsList = data
    }
}