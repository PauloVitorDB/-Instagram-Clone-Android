package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.*
import co.pvitor.instagram.common.util.RequestCallback

class ProfileLocalDataSource(
    private val profileCache: LocalCache<Pair<User, Boolean?>>,
    private val postsCache: LocalCache<List<Post>>
): ProfileDataSource {

    override fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>) {

        val postList = postsCache.get(uuid)

        if(postList != null) callback.onSuccess(postList) else callback.onFailure()

        callback.onComplete()

    }

    override fun fetchProfileUser(uuid: String, callback: RequestCallback<Pair<User, Boolean?>>) {

        val userAuth = profileCache.get(uuid)

        if(userAuth != null) callback.onSuccess(userAuth) else callback.onFailure()

        callback.onComplete()

    }

    override fun fetchSession(): User {
        return Database.sessionUserAuth ?: throw RuntimeException("User not found in session")
    }

    override fun putUser(response: Pair<User, Boolean?>?) {
        profileCache.set(response)
    }

    override fun putPostList(response: List<Post>?) {
        postsCache.set(response)
    }

}