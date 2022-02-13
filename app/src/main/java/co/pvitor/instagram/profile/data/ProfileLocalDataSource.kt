package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Database
import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

class ProfileLocalDataSource(
    private val profileCache: ProfileCache<UserAuth>,
    private val postsCache: ProfileCache<List<Post>>
): ProfileDataSource {

    override fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>) {

        val postList = postsCache.get(uuid)

        if(postList != null) callback.onSuccess(postList) else callback.onFailure()

        callback.onComplete()

    }

    override fun fetchProfileUser(uuid: String, callback: RequestCallback<UserAuth>) {

        val userAuth = profileCache.get(uuid)

        if(userAuth != null) callback.onSuccess(userAuth) else callback.onFailure()

        callback.onComplete()

    }

    override fun fetchSession(): UserAuth {
        return Database.sessionUserAuth ?: throw RuntimeException("User not found in session")
    }

    override fun putUser(response: UserAuth) {
        profileCache.put(response)
    }

    override fun putPostList(response: List<Post>) {
        postsCache.put(response)
    }

}