package co.pvitor.instagram.home.data

import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.util.RequestCallback

class HomeRemoteDataSource: HomeDataSource {

    override fun fetchPosts(uuid: String, callback: RequestCallback<List<Post>>) {

        val feed = Database.feedList[uuid]

        callback.onSuccess(feed?.toList() ?: emptyList())

        callback.onComplete()

    }

}