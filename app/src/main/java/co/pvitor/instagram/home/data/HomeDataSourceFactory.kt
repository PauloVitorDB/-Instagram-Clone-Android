package co.pvitor.instagram.home.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.Post

class HomeDataSourceFactory(
    private val feedListCache: LocalCache<List<Post>>
) {

    fun createDataSourceFromFeed() : HomeDataSource {
        return if(feedListCache.isCached()) {
            createLocalDataSource()
        } else {
            HomeRemoteDataSource()
        }
    }

    fun createLocalDataSource() : HomeLocalDataSource {
        return HomeLocalDataSource(feedListCache)
    }

}