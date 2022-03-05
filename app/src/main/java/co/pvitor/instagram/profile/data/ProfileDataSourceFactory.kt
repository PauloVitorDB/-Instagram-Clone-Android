package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.LocalCache
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.User

class ProfileDataSourceFactory(
    private val profileCache: LocalCache<Pair<User, Boolean?>>,
    private val postsCache: LocalCache<List<Post>>
) {

    fun createLocalDataSource() : ProfileDataSource {
        return ProfileLocalDataSource(profileCache, postsCache)
    }

    fun createRemoteDataSource() : ProfileDataSource {
        return FireProfileDataSource()
    }

    fun createFromUser(uuid: String?): ProfileDataSource {

         if(uuid == null && profileCache.isCached()) {
            return createLocalDataSource()
        }

        return createRemoteDataSource()
    }

    fun createFromPosts(uuid: String?): ProfileDataSource {

        if(uuid == null && postsCache.isCached()) {
            return createLocalDataSource()
        }

        return createRemoteDataSource()
    }

}