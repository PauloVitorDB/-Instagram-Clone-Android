package co.pvitor.instagram.home.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.util.RequestCallback

class HomeRepository(
    private val dataSourceFactory: HomeDataSourceFactory
) {

    fun fetchPosts(callback: RequestCallback<List<Post>>) {

        val localDataSource: HomeDataSource = dataSourceFactory.createLocalDataSource()

        val userAuth = localDataSource.fetchSession()

        val dataSource: HomeDataSource = dataSourceFactory.createDataSourceFromFeed()

        userAuth.uuid?.let {
            dataSource.fetchPosts(it, object: RequestCallback<List<Post>> {
                override fun onSuccess(response: List<Post>) {
                    localDataSource.putFeedList(response)
                    callback.onSuccess(response)
                }

                override fun onFailure() {
                    callback.onFailure()
                }

                override fun onComplete() {
                    callback.onComplete()
                }
            })
        }
    }

    fun clearCache() {
        val localDataSource = dataSourceFactory.createLocalDataSource()
        localDataSource.putFeedList(null)
    }

    fun logout() {
        dataSourceFactory.createRemoteDataSource().logout()
    }

}