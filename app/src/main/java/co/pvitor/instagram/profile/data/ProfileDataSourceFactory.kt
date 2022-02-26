package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth

class ProfileDataSourceFactory(
    private val profileCache: LocalCache<UserAuth>,
    private val postsCache: LocalCache<List<Post>>
) {

    fun createLocalDataSource() : ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createFromUser(): ProfileDataSource {

        if(profileCache.isCached()) {
            return createLocalDataSource()
        }

        return ProfileFakeDataSource()
    }

    fun createFromPosts(): ProfileDataSource {

        if(postsCache.isCached()) {
            return createLocalDataSource()
        }

        return ProfileFakeDataSource()
    }

}