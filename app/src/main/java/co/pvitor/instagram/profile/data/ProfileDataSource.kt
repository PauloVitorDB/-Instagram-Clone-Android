package co.pvitor.instagram.profile.data

import co.pvitor.instagram.common.model.Post
import co.pvitor.instagram.common.model.UserAuth
import co.pvitor.instagram.common.util.RequestCallback

interface ProfileDataSource {

    fun fetchProfileUser(uuid: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>)
    fun fetchProfilePosts(uuid: String, callback: RequestCallback<List<Post>>)

    fun followUser(uuid: String, isFollow: Boolean, callback: RequestCallback<Boolean>) { throw UnsupportedOperationException() }

    fun fetchSession() : UserAuth { throw UnsupportedOperationException() }
    fun putUser(response: Pair<UserAuth, Boolean?>?) { throw UnsupportedOperationException() }
    fun putPostList(response: List<Post>?) { throw UnsupportedOperationException() }

}