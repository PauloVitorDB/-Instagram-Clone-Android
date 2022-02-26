package co.pvitor.instagram.home.data

import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class HomeLocalDataSource(private val feedListCache: LocalCache<List<Post>>): HomeDataSource {

    override fun fetchPosts(uuid: String, callback: RequestCallback<List<Post>>) {

        val feedList: List<Post>? = feedListCache.get(uuid)

        callback.onSuccess(feedList ?: emptyList())

        callback.onComplete()

    }

    override fun fetchSession(): UserAuth {
        return Database.sessionUserAuth ?: throw RuntimeException()
    }

    override fun putFeedList(data: List<Post>?) {
        feedListCache.set(data)
    }

}